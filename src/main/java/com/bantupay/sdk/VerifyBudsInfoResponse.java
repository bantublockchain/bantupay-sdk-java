package com.bantupay.sdk;

public class VerifyBudsInfoResponse {
    private String publicKey;

    @Override
    public String toString() {
        return "VerifyBudInfoResponse{"+
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
