package com.example.Library.Util.customExceptions.relatedToCredentials;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException(String message) {
        super(message);
    }
}
