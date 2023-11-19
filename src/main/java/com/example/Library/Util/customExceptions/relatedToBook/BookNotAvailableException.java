package com.example.Library.Util.customExceptions.relatedToBook;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
