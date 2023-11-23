package com.example.Library.util.customExceptions.relatedToUser;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
