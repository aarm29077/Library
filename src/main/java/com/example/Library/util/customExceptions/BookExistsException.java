package com.example.Library.util.customExceptions;

public class BookExistsException extends RuntimeException{
    public BookExistsException(String message) {
        super(message);
    }
}
