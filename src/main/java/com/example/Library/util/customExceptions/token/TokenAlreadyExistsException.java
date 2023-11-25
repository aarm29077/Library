package com.example.Library.util.customExceptions.token;

public class TokenAlreadyExistsException extends RuntimeException{
    public TokenAlreadyExistsException(String message) {
        super(message);
    }
}
