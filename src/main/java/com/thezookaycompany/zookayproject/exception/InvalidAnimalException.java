package com.thezookaycompany.zookayproject.exception;

public class InvalidAnimalException extends RuntimeException{
    public InvalidAnimalException (String error ) {
        super(error);
    }
}
