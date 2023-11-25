package com.example.Library.util.customExceptions.author;

public class AuthorExistsException extends RuntimeException{
    public AuthorExistsException(String message) {
        super(message);
    }
}
