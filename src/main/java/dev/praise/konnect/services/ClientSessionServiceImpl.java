package dev.praise.konnect.services;

import dev.praise.konnect.data.models.ClientSession;
import dev.praise.konnect.data.repositories.ClientSessionRepository;
import dev.praise.konnect.dtos.response.CreateSessionResponse;
import dev.praise.konnect.exceptions.InvalidTokenException;
import dev.praise.konnect.services.ClientSessionService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class ClientSessionServiceImpl implements ClientSessionService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final ClientSessionRepository clientSessionRepository;

    public ClientSessionServiceImpl(ClientSessionRepository clientSessionRepository) {
        this.clientSessionRepository = clientSessionRepository;
    }

    @Override
    public CreateSessionResponse createSession() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        ClientSession session = new ClientSession();
        session.setTokenHash(hash(token));
        session.setCreatedAt(LocalDateTime.now());
        ClientSession saved = clientSessionRepository.save(session);

        return new CreateSessionResponse(saved.getClientId(), token);
    }

    @Override
    public String resolveClientId(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException();
        }
        return clientSessionRepository.findByTokenHash(hash(token))
                .map(ClientSession::getClientId)
                .orElseThrow(()-> new InvalidTokenException("Invalid message"));
    }

    private String hash(String token) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}