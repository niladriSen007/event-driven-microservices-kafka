package com.niladri.notification_service.error;

public class NonRetryable extends RuntimeException  {

    public NonRetryable(String message) {
        super(message);
    }

    public NonRetryable(Throwable cause) {
        super(cause);
    }
}
