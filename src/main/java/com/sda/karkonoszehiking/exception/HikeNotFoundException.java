package com.sda.karkonoszehiking.exception;

public class HikeNotFoundException extends RuntimeException {

    public HikeNotFoundException(String message){
        super(message);
    }
}