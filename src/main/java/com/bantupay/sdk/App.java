package com.bantupay.sdk;// create a completely new and unique pair of keys.
// see more about KeyPair objects: https://stellar.github.io/java-stellar-sdk/org/stellar/sdk/KeyPair.html
import org.stellar.sdk.KeyPair;
import shadow.com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

class  App {

    public static KeyPairModel createAccount() {
        KeyPair pair = KeyPair.random();
        KeyPairModel model = new KeyPairModel(pair.getSecretSeed().toString(), pair.getAccountId());
        return model;
    }

    public static String signHTTP(String uri, String secretSeed) {
        KeyPair pair = KeyPair.fromSecretSeed(secretSeed);
        byte [] signedData = pair.sign(uri.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(signedData);
        return encoded;
    }

    public static String signTxt (String secretSeed, String transactionXDR, String networkPhrase) {

        return "hi";
    }

    public static void main (String[] args) {
        JsonObject obj = new JsonObject();
        System.out.println(signHTTP("/v2/user/", "SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI"));
    }
}