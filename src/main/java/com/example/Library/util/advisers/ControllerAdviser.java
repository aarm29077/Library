package com.example.Library.util.advisers;

import com.example.Library.util.customExceptions.author.AuthorExistsException;
import com.example.Library.util.customExceptions.author.AuthorNotCreatedException;
import com.example.Library.util.customExceptions.author.AuthorNotFoundException;
import com.example.Library.util.customExceptions.book.BookExistsException;
import com.example.Library.util.customExceptions.book.BookNotAvailableException;
import com.example.Library.util.customExceptions.book.BookNotCreatedException;
import com.example.Library.util.customExceptions.book.BookNotFoundException;
import com.example.Library.util.customExceptions.credentials.UsernameExistsException;
import com.example.Library.util.customExceptions.mainAdmin.MainAdminDeletionException;
import com.example.Library.util.customExceptions.role.RoleNotFoundException;
import com.example.Library.util.customExceptions.token.TokenAlreadyExistsException;
import com.example.Library.util.customExceptions.user.UserExistsException;
import com.example.Library.util.customExceptions.user.UserNotCreatedException;
import com.example.Library.util.customExceptions.user.UserNotFoundException;
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
    private ResponseEntity<ErrorResponse> handleException(AuthorNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AuthorExistsException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AuthorNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BookNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BookExistsException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BookNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BookNotAvailableException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserExistsException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RoleNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UsernameExistsException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(TokenAlreadyExistsException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MainAdminDeletionException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
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

