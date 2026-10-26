package dev.praise.konnect.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

    @Column(name = "sender_alias", nullable = false)
    private String senderAlias;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Convert(converter = dev.praise.konnect.config.MessageCryptoConverter.class)
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name ="created_at", nullable = false)
    private LocalDateTime createdAt;

}