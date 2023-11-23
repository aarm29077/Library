package com.example.Library.util.customExceptions.relatedToToken;

public class TokenAlreadyExistsException extends RuntimeException{
    public TokenAlreadyExistsException(String message) {
        super(message);
    }
}
