package dev.praise.konnect.services;

import dev.praise.konnect.dtos.response.CreateSessionResponse;

public interface ClientSessionService {
    CreateSessionResponse createSession();
    String resolveClientId(String token);

}
