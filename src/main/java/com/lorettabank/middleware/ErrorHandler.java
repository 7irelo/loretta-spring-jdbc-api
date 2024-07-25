package com.lorettabank.middleware;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse("Server error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class ErrorResponse {
        private boolean success = false;
        private String message;
        private String error;

        public ErrorResponse(String message, String error) {
            this.message = message;
            this.error = error;
        }

        // Getters and setters
    }
}
