package com.thezookaycompany.zookayproject.exception;
public class InvalidTicketException extends RuntimeException {
    public InvalidTicketException(String error) {
        super(error);
    }
}
