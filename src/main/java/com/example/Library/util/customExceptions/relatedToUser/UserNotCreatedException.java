package com.example.Library.util.customExceptions.relatedToUser;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message) {
        super(message);
    }
}
