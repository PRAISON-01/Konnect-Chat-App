package dev.praise.konnect.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateRoomResponse {
    private String roomCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String roomName;
}
