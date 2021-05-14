package com.bantupay.sdk;

public class CreateAccountResponse {
    private String publicKey;

    @Override
    public String toString() {
        return "CreateBudInfoResponse{"+
                "publicKey='" + publicKey + '\'' +
                '}';
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
