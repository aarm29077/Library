package com.example.Library.util.advisers;

import com.example.Library.util.customExceptions.AuthorNotCreatedException;
import com.example.Library.util.customExceptions.BookNotCreatedException;
import com.example.Library.util.errorResponse.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviser {

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);//404
    }

//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//404
//    }

//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BookNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AuthorNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(IllegalStateException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(Exception e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);//500
//    }

}

