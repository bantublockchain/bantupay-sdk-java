package com.bantupay.sdk;

public class KeyPairModel {

    private String secretSeed;
    private String accountId;


    public KeyPairModel(String secretSeed, String accountId) {
        this.secretSeed = secretSeed;
        this.accountId = accountId;
    }

    public String getSecretSeed() {
        return secretSeed;
    }

    public void setSecretSeed(String secretSeed) {
        this.secretSeed = secretSeed;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
