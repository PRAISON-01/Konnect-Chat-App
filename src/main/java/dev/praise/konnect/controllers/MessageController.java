package dev.praise.konnect.controllers;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.dtos.request.DeleteMessageRequest;
import dev.praise.konnect.dtos.request.ValidateTimeLimitRequest;
import dev.praise.konnect.dtos.response.DeleteMessageResponse;
import dev.praise.konnect.dtos.response.MessageResponse;
import dev.praise.konnect.exceptions.MessageNotFoundException;
import dev.praise.konnect.exceptions.RoomNotFoundException;
import dev.praise.konnect.services.ClientSessionService;
import dev.praise.konnect.services.MessageService;
import dev.praise.konnect.services.RateLimiterService;
import dev.praise.konnect.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rooms/{roomCode}/messages")
public class MessageController {

    private final MessageService messageService;
    private final ClientSessionService clientSessionService;
    private final RateLimiterService rateLimiterService;
    private final RoomService roomService;

    public MessageController(MessageService messageService,
                             ClientSessionService clientSessionService,
                             RateLimiterService rateLimiterService,
                             RoomService roomService) {
        this.messageService = messageService;
        this.clientSessionService = clientSessionService;
        this.rateLimiterService = rateLimiterService;
        this.roomService = roomService;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable String roomCode,
            @RequestHeader("X-Client-Token") String clientToken,
            @RequestBody Message messageInput) throws RoomNotFoundException {

        String verifiedClientId = clientSessionService.resolveClientId(clientToken);

        ValidateTimeLimitRequest limitRequest = new ValidateTimeLimitRequest();
        limitRequest.setClientId(verifiedClientId);

        rateLimiterService.ValidateTimeLimit(limitRequest);

        Room room = roomService.getRoomByCode(roomCode);

        messageInput.setRoom(room);
        messageInput.setClientId(verifiedClientId);

        Message savedMessage = messageService.sendMessage(messageInput);

        MessageResponse response = convertToMessageResponse(savedMessage);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getMessage")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable String roomCode) throws RoomNotFoundException {
        Room room = roomService.getRoomByCode(roomCode);

        List<Message> databaseMessages = messageService.getMessagesByRoom(room);

        List<MessageResponse> responseList = new ArrayList<>();
        for (Message msg : databaseMessages) {
            responseList.add(convertToMessageResponse(msg));
        }

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<DeleteMessageResponse> deleteMessage(
            @PathVariable String roomCode,
            @PathVariable Long messageId,
            @RequestHeader("X-Client-Token") String clientToken) throws MessageNotFoundException {

        String verifiedClientId = clientSessionService.resolveClientId(clientToken);

        DeleteMessageRequest deleteRequest = new DeleteMessageRequest();
        deleteRequest.setMessageId(messageId);
        deleteRequest.setClientId(verifiedClientId);

        DeleteMessageResponse receipt = messageService.deleteMessage(deleteRequest);

        return ResponseEntity.ok(receipt);
    }

    private MessageResponse convertToMessageResponse(Message msg) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setId(msg.getId());

        if (msg.getRoom() != null) {
            messageResponse.setRoomCode(msg.getRoom().getRoomCode());
        }

        messageResponse.setSenderAlias(msg.getSenderAlias());
        messageResponse.setContent(msg.getContent());
        messageResponse.setCreatedAt(msg.getCreatedAt());
        return messageResponse;
    }
}
