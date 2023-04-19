package com.example.smartgarage.exceptions;

public class NotFoundRoleException extends RuntimeException {
    public NotFoundRoleException(String message) {
        super(message);
    }
}
