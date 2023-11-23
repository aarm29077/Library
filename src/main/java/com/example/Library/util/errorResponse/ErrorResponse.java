package com.example.Library.util.errorResponse;


import lombok.Getter;
import lombok.Setter;

public class ErrorResponse {
    private @Getter @Setter String message;
    private @Getter @Setter long timestamp;

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
