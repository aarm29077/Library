package com.example.Library.Util.customExceptions.relatedToAuthor;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
