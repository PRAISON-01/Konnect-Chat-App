package dev.praise.konnect.data.repositories;

import dev.praise.konnect.data.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
