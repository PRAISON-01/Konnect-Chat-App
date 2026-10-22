package dev.praise.konnect.dtos.response;

import dev.praise.konnect.data.models.Room;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ValidateTimeLimitResponse {
    private String clientId;
    private boolean allowed;
    private int remainingMessages;
    private int seconds;
}
