package com.bantupay.sdk;

public class PaymentRequest {
    private String username;
    private String destination;
    private String amount;
    private String memo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public static PaymentRequest getInstance(String username, String destination, String memo, String amount){
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.username = username;
        paymentRequest.destination = destination;
        paymentRequest.memo = memo;
        paymentRequest.amount = amount;

        return paymentRequest;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "username='" + username + '\'' +
                ", destination='" + destination + '\'' +
                ", amount='" + amount + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
