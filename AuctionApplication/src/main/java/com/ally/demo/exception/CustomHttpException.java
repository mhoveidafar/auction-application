package com.ally.demo.exception;

import lombok.Getter;

@Getter
public class CustomHttpException extends RuntimeException {
    private final int status;
    private final String reason;

    public CustomHttpException(int status, String reason) {
        super(status + " error: " + reason);
        this.status = status;
        this.reason = reason;
    }
}
