package com.niladri.notification_service.error;

public class Retryable extends RuntimeException {

    public Retryable(String message) {
        super(message);
    }

    public Retryable(Throwable cause) {
        super(cause);
    }

}
