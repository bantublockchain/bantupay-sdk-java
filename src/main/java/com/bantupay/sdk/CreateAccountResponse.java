package com.bantupay.sdk;

public class CreateAccountResponse {
    private String message;

    @Override
    public String toString() {
        return "CreateBudInfoResponse{"+
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String publicKey) {
        this.message = publicKey;
    }
}
