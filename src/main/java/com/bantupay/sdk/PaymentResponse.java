package com.bantupay.sdk;

public class PaymentResponse {
    private String destination;
    private String memo;
    private String assetIssuer;
    private String assetCode;
    private String amount;
    private String transaction;
    private String transactionSignature;
    private String transactionId;
    private String networkPassPhrase;
    private String destinationFirstName;
    private String destinationLastName;
    private String destinationThumbnail;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAssetIssuer() {
        return assetIssuer;
    }

    public void setAssetIssuer(String assetIssuer) {
        this.assetIssuer = assetIssuer;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getTransactionSignature() {
        return transactionSignature;
    }

    public void setTransactionSignature(String transactionSignature) {
        this.transactionSignature = transactionSignature;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getNetworkPassPhrase() {
        return networkPassPhrase;
    }

    public void setNetworkPassPhrase(String networkPassPhrase) {
        this.networkPassPhrase = networkPassPhrase;
    }

    public String getDestinationFirstName() {
        return destinationFirstName;
    }

    public void setDestinationFirstName(String destinationFirstName) {
        this.destinationFirstName = destinationFirstName;
    }

    public String getDestinationLastName() {
        return destinationLastName;
    }

    public void setDestinationLastName(String destinationLastName) {
        this.destinationLastName = destinationLastName;
    }

    public String getDestinationThumbnail() {
        return destinationThumbnail;
    }

    public void setDestinationThumbnail(String destinationThumbnail) {
        this.destinationThumbnail = destinationThumbnail;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "destination='" + destination + '\'' +
                ", memo='" + memo + '\'' +
                ", assetIssuer='" + assetIssuer + '\'' +
                ", assetCode='" + assetCode + '\'' +
                ", amount='" + amount + '\'' +
                ", transaction='" + transaction + '\'' +
                ", transactionSignature='" + transactionSignature + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", networkPassPhrase='" + networkPassPhrase + '\'' +
                ", destinationFirstName='" + destinationFirstName + '\'' +
                ", destinationLastName='" + destinationLastName + '\'' +
                ", destinationThumbnail='" + destinationThumbnail + '\'' +
                '}';
    }
}
