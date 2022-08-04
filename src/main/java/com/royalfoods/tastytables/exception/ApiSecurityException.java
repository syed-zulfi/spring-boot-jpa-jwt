package com.royalfoods.tastytables.exception;

public class ApiSecurityException extends RuntimeException{

    public ApiSecurityException(String message){
        super(message);
    }

    public ApiSecurityException(String message,Throwable throwable){
        super(message,throwable);
    }
}
