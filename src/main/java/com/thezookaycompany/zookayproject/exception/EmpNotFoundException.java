package com.thezookaycompany.zookayproject.exception;

public class EmpNotFoundException extends RuntimeException{
    public EmpNotFoundException(Long id) {
        super("Could not find emp id " + id);
    }
}
