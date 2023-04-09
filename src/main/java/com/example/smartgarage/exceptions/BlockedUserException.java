package com.example.smartgarage.exceptions;


public class BlockedUserException extends RuntimeException {
    public BlockedUserException(String message) {
        super(message);
    }
}

