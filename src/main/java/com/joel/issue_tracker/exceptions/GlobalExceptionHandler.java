package com.joel.issue_tracker.exceptions;


import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // response builder method
    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(status, message);
        return new ResponseEntity<>(error,status);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }




}