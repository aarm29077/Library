package com.example.Library.util.customExceptions;

public class InvalidAuthorException extends RuntimeException{
    public InvalidAuthorException(String message) {
        super(message);
    }
}
