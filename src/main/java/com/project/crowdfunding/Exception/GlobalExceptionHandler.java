package com.project.crowdfunding.Exception;


import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest servletRequest;


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){

        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Not found!", message, servletRequest.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex){

        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Illegal Argument found!", message, servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex){

        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("Exception occur!", message, servletRequest.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(KycNotVerifiedException.class)
    public ResponseEntity<ApiResponse> handleApiException(KycNotVerifiedException ex){

        String message = ex.getMessage();
        return new ResponseEntity<>(ApiResponse.error("KYC Required!", message, servletRequest.getRequestURI()), HttpStatus.FORBIDDEN);
    }

}
