package com.thezookaycompany.zookayproject.exception;

import org.aspectj.weaver.ast.Or;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }

}
