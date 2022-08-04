package com.royalfoods.tastytables.exception;

public class NullObjectException extends RuntimeException{

    private NullObjectException(){

    }
    public NullObjectException(String message) {
      super(message);
    }

   public NullObjectException(String message, Throwable t) {
        super(message,t);
   }
}
