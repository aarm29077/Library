package com.example.Library.util.customExceptions.book;

public class BookExistsException extends RuntimeException{
    public BookExistsException(String message) {
        super(message);
    }
}
