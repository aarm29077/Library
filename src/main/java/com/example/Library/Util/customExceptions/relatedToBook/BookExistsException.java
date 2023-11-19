package com.example.Library.Util.customExceptions.relatedToBook;

public class BookExistsException extends RuntimeException{
    public BookExistsException(String message) {
        super(message);
    }
}
