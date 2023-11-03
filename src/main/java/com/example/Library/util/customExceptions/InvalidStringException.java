package com.example.Library.util.customExceptions;

public class InvalidStringException extends RuntimeException{
    public InvalidStringException(String message) {
        super(message);
    }
}
