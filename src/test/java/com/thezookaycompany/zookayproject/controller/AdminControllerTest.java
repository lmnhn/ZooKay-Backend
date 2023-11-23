package com.thezookaycompany.zookayproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @Test
    void adminAccess() {
        assertEquals("Admin accessed", adminController.adminAccess());
    }
}