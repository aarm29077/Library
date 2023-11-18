package com.example.Library.util.customExceptions.relatedToBook;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
