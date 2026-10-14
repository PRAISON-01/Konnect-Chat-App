package dev.praise.konnect.exceptions;

public class RateLimiterException extends RuntimeException {
    public RateLimiterException(String message) {
        super(message);
    }

    public RateLimiterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateLimiterException(Throwable cause) {
        super(cause);
    }

    public RateLimiterException() {
        super();
    }
}
