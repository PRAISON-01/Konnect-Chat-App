package dev.praise.konnect.services;

import dev.praise.konnect.data.repositories.MessageRepository;
import dev.praise.konnect.dtos.request.ValidateTimeLimitRequest;
import dev.praise.konnect.exceptions.RateLimiterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RateLimiterServiceTest {
    @Mock

    private MessageRepository messageRepository;

    @InjectMocks
    private RateLimiterServiceImpl rateLimiterService;

    @Test
    public void testThat_rateLimiterServiceShouldAllowMessageBelowRateLimit() {
        String clientId = "Client-ID";

        ValidateTimeLimitRequest request = new ValidateTimeLimitRequest();
        request.setClientId(clientId);
        Mockito.when(messageRepository.countByClientIdAndCreatedAtAfter(any(String.class), any(LocalDateTime.class))).thenReturn(3L);
        assertDoesNotThrow(()-> {rateLimiterService.ValidateTimeLimit(request);
        });
    }

    @Test
    public void testThat_rateLimiterServiceShouldNotAllowMessageAboveRateLimit() {
        String clientId = "Client-ID";

        ValidateTimeLimitRequest request = new ValidateTimeLimitRequest();
        request.setClientId(clientId);


        Mockito.when(messageRepository.countByClientIdAndCreatedAtAfter(any(String.class), any(LocalDateTime.class))).thenReturn(5L);

        RateLimiterException exception = assertThrows(
                RateLimiterException.class,
                ()-> rateLimiterService.ValidateTimeLimit(request)
        );

        assertEquals("Spam protection triggered. You are restricted to 5 messages every 30 seconds", exception.getMessage());
    }

}
