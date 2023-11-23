package com.thezookaycompany.zookayproject.exception;

public class InvalidVoucherException extends RuntimeException{
    public InvalidVoucherException(String error){
        super(error);
    }
}
