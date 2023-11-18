package com.example.Library.util.customExceptions.relatedToUser;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
