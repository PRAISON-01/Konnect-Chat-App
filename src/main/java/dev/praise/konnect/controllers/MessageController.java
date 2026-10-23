package dev.praise.konnect.controllers;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.dtos.request.SendMessageRequest;
import dev.praise.konnect.dtos.request.ValidateTimeLimitRequest;
import dev.praise.konnect.dtos.response.MessageResponse;
import dev.praise.konnect.exceptions.RoomNotFoundException;
import dev.praise.konnect.services.ClientSessionService;
import dev.praise.konnect.services.MessageService;
import dev.praise.konnect.services.RateLimiterService;
import dev.praise.konnect.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/rooms/{roomCode}/messages")   // was "/rooms/{roomCode}/messages"
public class MessageController {



    private final ClientSessionService clientSessionService;
    private final RateLimiterService rateLimiterService;
    private final RoomService roomService;
    private final MessageService messageService;


    public MessageController(ClientSessionService clientSessionService, RateLimiterService rateLimiterService, RoomService roomService, MessageService messageService) {
        this.clientSessionService = clientSessionService;
        this.rateLimiterService = rateLimiterService;
        this.roomService = roomService;
        this.messageService = messageService;
    }

    @PostMapping                                     
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable String roomCode,
            @RequestHeader("X-Client-Token") String clientToken,
            @RequestBody SendMessageRequest body) throws RoomNotFoundException {

        String clientId = clientSessionService.resolveClientId(clientToken);

        ValidateTimeLimitRequest limitRequest = new ValidateTimeLimitRequest();
        limitRequest.setClientId(clientId);
        rateLimiterService.ValidateTimeLimit(limitRequest);

        Room room = roomService.getRoomByCode(roomCode);

        Message message = new Message();
        message.setRoom(room);
        message.setClientId(clientId);
        message.setContent(body.getContent());

        Message saved = messageService.sendMessage(message);
        return new ResponseEntity<>(toResponse(saved, clientId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages(
            @PathVariable String roomCode,
            @RequestHeader("X-Client-Token") String clientToken) throws RoomNotFoundException {

        String clientId = clientSessionService.resolveClientId(clientToken);
        Room room = roomService.getRoomByCode(roomCode);

        List<MessageResponse> list = messageService.getMessagesByRoom(room).stream()
                .map(m -> toResponse(m, clientId))
                .toList();
        return ResponseEntity.ok(list);
    }

    private MessageResponse toResponse(Message msg, String viewerClientId) {
        MessageResponse r = new MessageResponse();
        r.setId(msg.getId());
        if (msg.getRoom() != null) r.setRoomCode(msg.getRoom().getRoomCode());
        r.setSenderAlias(msg.getSenderAlias());
        r.setContent(msg.getContent());
        r.setCreatedAt(msg.getCreatedAt());
        r.setMine(msg.getClientId().equals(viewerClientId));   // never send clientId itself
        return r;
    }
}