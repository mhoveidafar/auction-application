package com.ally.demo.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(int id) {
        super("item with id:" + id + " not found");
    }
}
