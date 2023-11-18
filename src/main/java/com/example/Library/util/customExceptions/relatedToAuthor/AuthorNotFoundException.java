package com.example.Library.util.customExceptions.relatedToAuthor;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
