package com.example.Library.Util.customExceptions.relatedToBook;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
