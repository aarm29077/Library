package com.example.Library.Util.customExceptions.relatedToRole;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
