package dev.praise.konnect.dtos.request;

import dev.praise.konnect.data.models.Message;
import lombok.Data;

@Data
public class DeleteMessageRequest {
    private Long messageId;
    private String clientId;
}

