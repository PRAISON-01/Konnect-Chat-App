package dev.praise.konnect.exceptions;

public class NotMessageOwnerException extends RuntimeException {
    public NotMessageOwnerException(String message) {
        super(message);
    }



    public NotMessageOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMessageOwnerException(Throwable cause){
        super(cause);
    }

    public NotMessageOwnerException() {
        super();

    }


}
