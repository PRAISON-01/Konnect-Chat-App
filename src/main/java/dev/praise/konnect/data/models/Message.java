package dev.praise.konnect.data.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table()
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roo_id",nullable = false)
    private Room room;

    @Column(name = "sender_alias", nullable = false)
    private String senderAlias;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name ="created_at", nullable = false)
    private LocalDateTime createdAt;

    public Message(Long id, Room room, String senderAlias, String clientId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.room = room;
        this.senderAlias = senderAlias;
        this.clientId = clientId;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", room=" + room +
                ", senderAlias='" + senderAlias + '\'' +
                ", clientId='" + clientId + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSenderAlias() {
        return senderAlias;
    }

    public void setSenderAlias(String senderAlias) {
        this.senderAlias = senderAlias;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
