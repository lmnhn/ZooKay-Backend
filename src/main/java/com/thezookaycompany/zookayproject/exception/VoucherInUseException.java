package com.thezookaycompany.zookayproject.exception;

public class VoucherInUseException extends RuntimeException{
    public VoucherInUseException (String error ) {
        super(error);
    }
}
