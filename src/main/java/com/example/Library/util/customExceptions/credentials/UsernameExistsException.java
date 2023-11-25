package com.example.Library.util.customExceptions.credentials;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException(String message) {
        super(message);
    }
}
