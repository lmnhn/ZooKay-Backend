package com.thezookaycompany.zookayproject.model.dto;

import com.thezookaycompany.zookayproject.model.entity.Account;

public class LoginResponse {

    private Account account;
    private String jwt;

    public LoginResponse() {
        super();
    }

    public LoginResponse(Account account, String jwt) {
        this.account = account;
        this.jwt = jwt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
