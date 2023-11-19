package com.example.Library.Util.customExceptions.relatedToUser;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
