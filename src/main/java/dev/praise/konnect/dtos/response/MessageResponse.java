package dev.praise.konnect.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MessageResponse {
    private Long id;
    private String roomCode;
    private String senderAlias;
    private String content;
    private LocalDateTime createdAt;
}
