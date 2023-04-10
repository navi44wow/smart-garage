package com.example.smartgarage.exceptions;

public class NotValidPasswordException extends RuntimeException{
    public NotValidPasswordException(String message) { super(message);}
}
