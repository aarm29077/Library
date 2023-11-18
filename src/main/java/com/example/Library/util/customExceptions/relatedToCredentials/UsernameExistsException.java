package com.example.Library.util.customExceptions.relatedToCredentials;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException(String message) {
        super(message);
    }
}
