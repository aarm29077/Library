package com.example.Library.util.customExceptions;

public class CustomerNotCreatedException extends RuntimeException {
    public CustomerNotCreatedException(String message) {
        super(message);
    }
}
