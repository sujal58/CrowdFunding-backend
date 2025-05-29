package com.project.crowdfunding.Exception;

public class ApiException extends RuntimeException{

    public ApiException(String message){
        super(message);
    }
}
