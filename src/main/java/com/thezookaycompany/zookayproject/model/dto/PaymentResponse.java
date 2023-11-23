package com.thezookaycompany.zookayproject.model.dto;

public class PaymentResponse {
    private String clientSecret;

    private String intentId;

    public PaymentResponse(String clientSecret, String intentId) {
        this.clientSecret = clientSecret;
        this.intentId = intentId;
    }

    public PaymentResponse(String intentId) {
        this.intentId = intentId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getIntentId() {
        return intentId;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }
}
