package com.example.Library.util.customExceptions.relatedToRole;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
