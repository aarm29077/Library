package com.example.Library.Util.customExceptions.relatedToUser;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
