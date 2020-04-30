package com.ally.demo.exception;

public class NotFoundException extends CustomHttpException {

    public NotFoundException(int id) {
        super(404, "item with id:" + id + " not found");
    }
}
