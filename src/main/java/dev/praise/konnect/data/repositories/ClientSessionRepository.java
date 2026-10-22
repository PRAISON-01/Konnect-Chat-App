package dev.praise.konnect.data.repositories;

import dev.praise.konnect.data.models.ClientSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientSessionRepository extends JpaRepository<ClientSession, String> {
    Optional<ClientSession> findByTokenHash(String tokenHash);
}