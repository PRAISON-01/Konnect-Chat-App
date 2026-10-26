package dev.praise.konnect.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);

        return new ResponseEntity<>(errorDetails, status);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidToken(InvalidTokenException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }


    @ExceptionHandler(NotMessageOwnerException.class)
    public ResponseEntity<Map<String, Object>> handleNotMessageOwner(NotMessageOwnerException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "You do not have permission to delete this message.");
    }


    @ExceptionHandler({MessageNotFoundException.class, RoomNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFound(Exception ex) {   // was RuntimeException
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(RateLimiterException.class)
    public ResponseEntity<Map<String, Object>> handleRateLimiting(RateLimiterException ex) {
        return buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
    }
}
