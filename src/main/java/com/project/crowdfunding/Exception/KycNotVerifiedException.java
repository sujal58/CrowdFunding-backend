package com.project.crowdfunding.Exception;

public class KycNotVerifiedException extends RuntimeException{

    public KycNotVerifiedException(String message){
        super(message);
    }
}
