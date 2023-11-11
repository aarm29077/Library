package com.example.Library.util.customExceptions;

public class AuthorExistsException extends RuntimeException{
    public AuthorExistsException(String message) {
        super(message);
    }
}
