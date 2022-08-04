package com.royalfoods.tastytables.exception;

public class UniqueRecordConstraintViolationException extends RuntimeException{


    public UniqueRecordConstraintViolationException(String message){
        super(message);
    }

}
