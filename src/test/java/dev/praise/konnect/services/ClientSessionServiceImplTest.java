package dev.praise.konnect.services;

import dev.praise.konnect.data.models.ClientSession;
import dev.praise.konnect.data.repositories.ClientSessionRepository;
import dev.praise.konnect.dtos.response.CreateSessionResponse;
import dev.praise.konnect.exceptions.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientSessionServiceImplTest {

    @Mock
    private ClientSessionRepository clientSessionRepository;

    @InjectMocks
    private ClientSessionServiceImpl clientSessionService;

    private ClientSession mockSavedSession;

    @BeforeEach
    void setUp() {
        mockSavedSession = new ClientSession();
        mockSavedSession.setClientId("mock-uuid-12345");
        mockSavedSession.setTokenHash("some-hashed-fingerprint");
        mockSavedSession.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createSession_savesToDatabase_andReturnsTokenAndId() {
        when(clientSessionRepository.save(any(ClientSession.class))).thenReturn(mockSavedSession);
        CreateSessionResponse response = clientSessionService.createSession();
        assertNotNull(response);
        assertEquals("mock-uuid-12345", response.getClientId());
        assertNotNull(response.getToken());
        assertFalse(response.getToken().isBlank());

        verify(clientSessionRepository, times(1)).save(any(ClientSession.class));
    }

    @Test
    void resolveClientId_withValidToken_returnsCorrectClientId() {
        when(clientSessionRepository.findByTokenHash(any(String.class)))
                .thenReturn(Optional.of(mockSavedSession));

        String resolvedId = clientSessionService.resolveClientId("my-secret-raw-token");

        assertEquals("mock-uuid-12345", resolvedId);
        verify(clientSessionRepository, times(1)).findByTokenHash(any(String.class));
    }

    @Test
    void resolveClientId_withUnknownOrFakeToken_throwsInvalidTokenException() {
        when(clientSessionRepository.findByTokenHash(any(String.class)))
                .thenReturn(Optional.empty());

        assertThrows(InvalidTokenException.class, () -> {
            clientSessionService.resolveClientId("fake-or-tampered-token");
        });
    }

    @Test
    void resolveClientId_withNullToken_throwsException_andNeverTouchesDatabase() {
        assertThrows(InvalidTokenException.class, () -> {
            clientSessionService.resolveClientId(null);
        });

        verifyNoInteractions(clientSessionRepository);
    }

    @Test
    void resolveClientId_withBlankToken_throwsException_andNeverTouchesDatabase() {
        assertThrows(InvalidTokenException.class, () -> {
            clientSessionService.resolveClientId("    ");
        });

        verifyNoInteractions(clientSessionRepository);
    }
}
