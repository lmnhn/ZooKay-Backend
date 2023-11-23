package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.EmpNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmpNotFoundException.class)
    public ResponseEntity<String> handleEmpNotFoundException(EmpNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
