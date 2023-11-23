package com.example.Library.util.customExceptions.relatedToBook;

public class BookExistsException extends RuntimeException{
    public BookExistsException(String message) {
        super(message);
    }
}
