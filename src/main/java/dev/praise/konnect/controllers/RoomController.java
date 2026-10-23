package dev.praise.konnect.controllers;

import dev.praise.konnect.data.models.Room;
import dev.praise.konnect.dtos.request.CreateRoomRequest;
import dev.praise.konnect.dtos.response.CreateRoomResponse;
import dev.praise.konnect.exceptions.RoomNotFoundException;
import dev.praise.konnect.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @PostMapping
    public ResponseEntity<CreateRoomResponse> createRoom(
            @RequestBody(required = false) CreateRoomRequest request) {
        if (request == null) request = new CreateRoomRequest();
        CreateRoomResponse response = roomService.createRoom(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @GetMapping("/{roomCode}")
    public ResponseEntity<Room> joinRoom(@PathVariable String roomCode) throws RoomNotFoundException {

        Room response = roomService.getRoomByCode(roomCode);
        return ResponseEntity.ok(response);
    }
}
