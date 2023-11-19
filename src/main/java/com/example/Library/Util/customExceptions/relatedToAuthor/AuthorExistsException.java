package com.example.Library.Util.customExceptions.relatedToAuthor;

public class AuthorExistsException extends RuntimeException{
    public AuthorExistsException(String message) {
        super(message);
    }
}
