package com.example.Library.util.customExceptions.relatedToBook;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
