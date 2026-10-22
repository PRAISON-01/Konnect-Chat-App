package dev.praise.konnect.controllers;

import dev.praise.konnect.dtos.response.CreateSessionResponse;
import dev.praise.konnect.services.ClientSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientSessionController {

    private final ClientSessionService clientSessionService;

    public ClientSessionController(ClientSessionService clientSessionService) {
        this.clientSessionService = clientSessionService;
    }

    @PostMapping
    public ResponseEntity<CreateSessionResponse> createSession() {
        CreateSessionResponse response = clientSessionService.createSession();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
