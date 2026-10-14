package dev.praise.konnect.services;

import dev.praise.konnect.dtos.request.ValidateTimeLimitRequest;
import dev.praise.konnect.dtos.response.ValidateTimeLimitResponse;

public interface RateLimiterService {
    ValidateTimeLimitResponse ValidateTimeLimit(ValidateTimeLimitRequest request);
}
