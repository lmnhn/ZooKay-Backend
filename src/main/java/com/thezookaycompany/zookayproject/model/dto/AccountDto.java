package com.thezookaycompany.zookayproject.model.dto;

public class AccountDto {

    private String email;
    private String username;
    private String password;
    private String phoneNumber;

    public AccountDto() {
    }

    public AccountDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountDto(String email) {
        this.email = email;
    }

    public AccountDto(String email, String username, String password, String phoneNumber) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
