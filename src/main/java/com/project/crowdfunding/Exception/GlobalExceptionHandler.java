package com.project.crowdfunding.Exception;


import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest servletRequest){
        log.error("Resource not found!", ex);
        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Resource Not found!", message, servletRequest.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest servletRequest){
        log.error("Username not found!", ex);
        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Username Not found!", message, servletRequest.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest servletRequest){
        log.error("Illegal argument found!", ex);
        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Illegal Argument found!", message, servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex, HttpServletRequest servletRequest){
        log.error("Api error occurred!", ex);
        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Exception occur!", message, servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KycNotVerifiedException.class)
    public ResponseEntity<ApiResponse> handleApiException(KycNotVerifiedException ex, HttpServletRequest servletRequest){
        log.error("Unexpected error occurred!", ex);
        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("KYC Required!", message, servletRequest.getRequestURI()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, HttpServletRequest servletRequest) {
        log.error("Unexpected error occurred", ex);
        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Exception occurred!", message, servletRequest.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest servletRequest) {
        log.error("Unexpected error occurred", ex);
        String message = ex.getMessage();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(ApiResponse.error("Validation error occurred!", errors, servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest servletRequest) {
        String message = ex.getMessage();
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing
                ));
        return new ResponseEntity<>(ApiResponse.error("Constraint violation occurred!", errors, servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);

    }

}
