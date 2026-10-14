package dev.praise.konnect.dtos.response;

import dev.praise.konnect.data.models.Room;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ValidateTimeLimitResponse {

    private Long id;

    private Room room;

    private String senderAlias;

    private String clientId;

    private String content;
}
