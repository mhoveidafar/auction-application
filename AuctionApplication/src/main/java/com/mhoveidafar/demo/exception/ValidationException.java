package com.mhoveidafar.demo.exception;

public class ValidationException extends CustomHttpException {
    public ValidationException(String reason) {
        super(400, reason);
    }
}
