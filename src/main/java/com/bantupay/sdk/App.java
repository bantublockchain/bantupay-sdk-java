package com.bantupay.sdk;// create a completely new and unique pair of keys.
// see more about KeyPair objects: https://stellar.github.io/java-stellar-sdk/org/stellar/sdk/KeyPair.html
import okhttp3.*;
import org.stellar.sdk.AbstractTransaction;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Network;
import shadow.com.google.gson.Gson;
import org.stellar.sdk.Transaction;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

class  App {

    public static KeyPairModel createAccount() {
        KeyPair pair = KeyPair.random();
        KeyPairModel model = new KeyPairModel(new String(pair.getSecretSeed()), pair.getAccountId());
        return model;
    }

    public static String signHTTP(String uri, String secretSeed, RequestBody requestBody) {
        KeyPair pair = KeyPair.fromSecretSeed(secretSeed);
        byte[] signedData;
        System.out.println(requestBody.toString());
        if(requestBody != null) {
            signedData = pair.sign(uri.concat(new Gson().toJson(requestBody.toString())).getBytes(StandardCharsets.UTF_8));
        }else{
            signedData = pair.sign(uri.getBytes(StandardCharsets.UTF_8));
        }
        String encoded = Base64.getEncoder().encodeToString(signedData);
        return encoded;
    }

    public static String signHTTP(String uri, String secretSeed, String encodedBody) {
        KeyPair pair = KeyPair.fromSecretSeed(secretSeed);
        byte[] signedData;
//        System.out.println(requestBody.toString());
        if(encodedBody != null) {
            signedData = pair.sign(uri.concat(encodedBody).getBytes(StandardCharsets.UTF_8));
        }else{
            signedData = pair.sign(uri.getBytes(StandardCharsets.UTF_8));
        }
        String encoded = Base64.getEncoder().encodeToString(signedData);
        return encoded;
    }

    public static String signTxn (String secretSeed, String transactionXDR, String networkPhrase) throws IOException {
        KeyPair pair = KeyPair.fromSecretSeed(secretSeed);
        Network ntwrkPhrase = new Network(networkPhrase);
        AbstractTransaction txn = Transaction.fromEnvelopeXdr(transactionXDR, ntwrkPhrase);
        byte [] signedTxn = pair.sign(txn.hash());
        String encoded = Base64.getEncoder().encodeToString(signedTxn);
        return encoded;
    }

    public static void main (String[] args) throws IOException {
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static PaymentResponse confirmPaymentDetail(
            String username,
            String destination,
            String memo,
            String amount,
            String secretKey,
            String assetIssuer,
            String assetCode,
            String transaction,
            String transactionSignature,
            String networkPhrase
    ){
        try {
            KeyPair keyPairModel = KeyPair.fromSecretSeed(secretKey);
            String publicKey = keyPairModel.getAccountId();
            String secretSeed = new String(keyPairModel.getSecretSeed());

            OkHttpClient client = new OkHttpClient();
            PaymentRequest paymentRequest = PaymentRequest.getInstance(
                    username, destination, memo, amount, "", "", "", "", ""
            );
            String encodedRequestBody = new Gson().toJson(paymentRequest);
            RequestBody body = RequestBody.create(JSON, encodedRequestBody);

            String signature = signHTTP("/v2/users/"+username+"/payments", secretSeed, encodedRequestBody);
            Request request = new Request.Builder()
                    .url("https://api.bantupay.org/v2/users/"+username+"/payments")
                    .post(body)
                    .addHeader("X-BANTUPAY-PUBLIC-KEY", publicKey)
                    .addHeader("X-BANTUPAY-SIGNATURE", signature)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if(response.isSuccessful()){
                PaymentResponse paymentResponse = new Gson().fromJson(response.body().string(), PaymentResponse.class);
                return paymentResponse;
            }else{
                PaymentErrorResponse paymentErrorResponse = new Gson().fromJson(response.body().string(), PaymentErrorResponse.class);
                throw new Exception(String.valueOf(paymentErrorResponse));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static PaymentResponse makePayment(
            String username,
            String destination,
            String memo,
            String amount,
            String secretKey,
            String assetIssuer,
            String assetCode,
            PaymentResponse serverResponse
    ){
        try {
            KeyPair keyPairModel = KeyPair.fromSecretSeed(secretKey);
            String publicKey = keyPairModel.getAccountId();
            String secretSeed = new String(keyPairModel.getSecretSeed());

            String transaction = serverResponse.getTransaction();
            String networkPhrase = serverResponse.getNetworkPassPhrase();
            String transactionSignature = signTxn(secretSeed, transaction, networkPhrase);

            OkHttpClient client = new OkHttpClient();
            PaymentRequest paymentRequest = PaymentRequest.getInstance(
                    username, destination, memo, amount, assetIssuer, assetCode, transaction,
                    transactionSignature, networkPhrase
            );
            String encodedRequestBody = new Gson().toJson(paymentRequest);
            RequestBody body = RequestBody.create(JSON, encodedRequestBody);

            String signature = signHTTP("/v2/users/"+username+"/payments", secretSeed, encodedRequestBody);
            Request request = new Request.Builder()
                    .url("https://api.bantupay.org/v2/users/"+username+"/payments")
                    .post(body)
                    .addHeader("X-BANTUPAY-PUBLIC-KEY", publicKey)
                    .addHeader("X-BANTUPAY-SIGNATURE", signature)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if(response.isSuccessful()){
                PaymentResponse paymentResponse = new Gson().fromJson(response.body().string(), PaymentResponse.class);
                return paymentResponse;
            }else{
                PaymentErrorResponse paymentErrorResponse = new Gson().fromJson(response.body().string(), PaymentErrorResponse.class);
                throw new Exception(String.valueOf(paymentErrorResponse));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PaymentResponse expressPay(
            String username,
            String destination,
            String memo,
            String amount,
            String secretKey,
            String assetIssuer,
            String assetCode
    ) {
        PaymentResponse res = confirmPaymentDetail(
                username,
                destination,
                memo,
                amount,
                secretKey,
                assetIssuer,
                assetCode,
                "",
                "",
                ""
        );
        PaymentResponse res2 = makePayment(
                username,
                destination,
                memo,
                amount,
                secretKey,
                assetIssuer,
                assetCode,
                res
        );
        return res2;
    }
}