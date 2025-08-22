package com.example.enotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.enotes.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        log.error("Unhandled exception occurred", e);
        return CommonUtil.createErrorResponseMessage(
                "An unexpected error occurred. Please contact support if the issue persists.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("Access denied: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(
                "You do not have permission to access this resource.",
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        log.warn("Authorization denied: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(
                "You are not authorized to perform this action.",
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> handleSuccessException(SuccessException e) {
        log.info("Success: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Invalid argument: {}", e.getMessage(), e);
        return CommonUtil.createErrorResponseMessage(
                "Invalid input provided. Please check your request.",
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        log.error("Null pointer exception occurred", e);
        return CommonUtil.createErrorResponseMessage(
                "A required value was missing. Please contact support if the issue persists.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("Resource not found: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(
                e.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        log.error("Validation failed: {}", e.getMessage(), e);
        return CommonUtil.createErrorResponse(
                e.getErrors(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(ExistDataException e) {
        log.warn("Data conflict: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(
                e.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Malformed JSON request: {}", e.getMessage(), e);
        return CommonUtil.createErrorResponseMessage(
                "Malformed request. Please check your input format.",
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
        log.warn("File not found: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(
                "Requested file could not be found.",
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        log.warn("Bad credentials: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(
                "Invalid username or password.",
                HttpStatus.BAD_REQUEST
        );
    }
}