package dev.praise.konnect.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="room_code", unique = true, nullable = false)
    private String roomCode;

    @Column(name  ="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "room_name", nullable = false)
    private String roomName;

}
