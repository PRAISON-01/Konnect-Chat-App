package dev.praise.konnect.dtos.request;

import dev.praise.konnect.data.models.Message;
import dev.praise.konnect.data.models.Room;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendMessageRequest {
    private Long id;
    private Room room;
    private String senderAlias;
    private String clientId;
    private String content;
    private LocalDateTime createdAt;

}
