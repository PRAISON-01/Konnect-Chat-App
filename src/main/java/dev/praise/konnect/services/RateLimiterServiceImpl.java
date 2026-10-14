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

        long messagesCount = messageRepository.countByClientIdAndCreatedAtAfter(request.getClientId(), LocalDateTime.now().minusSeconds(MAX_SECONDS));

        if(messagesCount >= MAX_MESSAGES) {
            log.warn("Rate Limit Reached Spam Attempt Detected try action again after 30 seconds");

            throw new RateLimiterException("Spam protection triggered. You are restricted to "+ MAX_MESSAGES +" messages every "+ MAX_SECONDS+ " seconds");
        }

        ValidateTimeLimitResponse reponse = new ValidateTimeLimitResponse();


        return null;
    }

}
