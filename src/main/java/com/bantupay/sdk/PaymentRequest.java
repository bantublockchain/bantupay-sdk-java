package com.bantupay.sdk;

public class PaymentRequest {
    private String username;
    private String destination;
    private String amount;
    private String memo;
    private String assetIssuer;
    private String assetCode;
    private String transaction;
    private String transactionSignature;
    private String networkPassPhrase;

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

    public String getAssetIssuer() { return assetIssuer; }

    public void setAssetIssuer(String assetIssuer) {
        this.assetIssuer = assetIssuer;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public void setTransactionSignature(String transactionSignature) {
        this.transactionSignature = transactionSignature;
    }

    public String getTransactionSignature() {
        return transactionSignature;
    }

    public String getNetworkPassPhrase() {
        return networkPassPhrase;
    }

    public void setNetworkPassPhrase(String networkPassPhrase) {
        this.networkPassPhrase = networkPassPhrase;
    }


    public static PaymentRequest getInstance(
            String username, String destination, String memo, String amount, String assetIssuer, String assetCode,
            String transaction, String transactionSignature, String networkPassPhrase
    ){
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.username = username;
        paymentRequest.destination = destination;
        paymentRequest.memo = memo;
        paymentRequest.amount = amount;
        paymentRequest.assetIssuer = assetIssuer;
        paymentRequest.assetCode = assetCode;
        paymentRequest.transaction = transaction;
        paymentRequest.transactionSignature = transactionSignature;
        paymentRequest.networkPassPhrase = networkPassPhrase;

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
