package dev.praise.konnect.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSessionResponse {
    private String clientId;
    private String token;
}