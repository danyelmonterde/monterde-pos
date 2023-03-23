package com.monterdev.monterdepos.exception;

public class POSException extends Exception{

    public POSException(String errorMessage, Throwable cause){
        super(errorMessage,cause);
    }
}
