package dev.praise.konnect.dtos.response;

import lombok.Data;

@Data
public class DeleteMessageResponse {
    private Long messageId;
    private String content;
}
