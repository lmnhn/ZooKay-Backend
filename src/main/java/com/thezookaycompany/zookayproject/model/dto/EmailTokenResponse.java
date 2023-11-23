package com.thezookaycompany.zookayproject.model.dto;

public class EmailTokenResponse {
    private String email;
    private String otp_token;



    public EmailTokenResponse(String email, String otp_token) {
        this.email = email;
        this.otp_token = otp_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp_token;
    }

    public void setOtp(String otp_token) {
        this.otp_token = otp_token;
    }
}
