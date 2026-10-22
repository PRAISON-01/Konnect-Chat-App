package dev.praise.konnect.services;

import dev.praise.konnect.data.repositories.MessageRepository;
import dev.praise.konnect.dtos.request.ValidateTimeLimitRequest;
import dev.praise.konnect.dtos.response.ValidateTimeLimitResponse;
import dev.praise.konnect.exceptions.RateLimiterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RateLimiterServiceImpl implements RateLimiterService{

    private static final Logger log = LoggerFactory.getLogger(RateLimiterService.class);

    @Autowired
    private MessageRepository messageRepository;

    private static final int MAX_MESSAGES = 5;
    private static final int MAX_SECONDS = 30;



    @Override
    public ValidateTimeLimitResponse ValidateTimeLimit(ValidateTimeLimitRequest request) {
        long count = messageRepository.countByClientIdAndCreatedAtAfter(request.getClientId(), LocalDateTime.now().minusSeconds(MAX_SECONDS));

        if (count >= MAX_MESSAGES) {
            log.warn("Rate limit reached for client {}", request.getClientId());
            throw new RateLimiterException("You're limited to " + MAX_MESSAGES + " messages every " + MAX_SECONDS + " seconds");
        }

        boolean allowed = count < MAX_MESSAGES;

        ValidateTimeLimitResponse response = new ValidateTimeLimitResponse();
        response.setClientId(request.getClientId());
        response.setAllowed(allowed);
        response.setRemainingMessages((int) (MAX_MESSAGES - count - 1));
        response.setSeconds(MAX_SECONDS);
        return response;
    }

}
