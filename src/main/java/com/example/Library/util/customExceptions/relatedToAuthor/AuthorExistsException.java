package com.example.Library.util.customExceptions.relatedToAuthor;

public class AuthorExistsException extends RuntimeException{
    public AuthorExistsException(String message) {
        super(message);
    }
}
