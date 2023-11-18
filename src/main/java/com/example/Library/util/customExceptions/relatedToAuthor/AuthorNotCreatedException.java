package com.example.Library.util.customExceptions.relatedToAuthor;

public class AuthorNotCreatedException extends RuntimeException {
    public AuthorNotCreatedException(String message) {
        super(message);
    }
}
