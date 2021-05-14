package com.bantupay.sdk;// create a completely new and unique pair of keys.
// see more about KeyPair objects: https://stellar.github.io/java-stellar-sdk/org/stellar/sdk/KeyPair.html
import okhttp3.*;
import org.stellar.sdk.AbstractTransaction;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Network;
import shadow.com.google.gson.Gson;
import org.stellar.sdk.Transaction;
import shadow.com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class  App {

    public static KeyPairModel createAccount() {
        KeyPair pair = KeyPair.random();
        KeyPairModel model = new KeyPairModel(new String(pair.getSecretSeed()), pair.getAccountId());
        return model;
    }

    public static String signHTTP(String uri, String secretSeed, String encodedBody) {
        KeyPair pair = KeyPair.fromSecretSeed(secretSeed);
        byte[] signedData;
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
        KeyPairModel key = createAccount();
        System.out.println(key.getSecretSeed());
        CreateAccountResponse acc = verfifyBudsInfo(
                "SCXJ643HXWK4LVWYED2ZD3QGGQV2L4YPYSIOL33O6TAZWND4D5Q7VJZ5",
                "meme3aaq1",
                "d.ifrentecho@gmail.com",
                "Last name",
                "firstname",
                "middlename",
                "+2349033701012",
                "https://api-alpha.dev.bantupay.org",
                "412509",
                "",
                "",
                ""
        );

//        System.out.println(new String(String.valueOf(acc)));
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static Response confirmPaymentDetail(
            String username,
            String destination,
            String memo,
            String amount,
            String secretKey,
            String baseURL,
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
                    .url(baseURL+"/v2/users/"+username+"/payments")
                    .post(body)
                    .addHeader("X-BANTUPAY-PUBLIC-KEY", publicKey)
                    .addHeader("X-BANTUPAY-SIGNATURE", signature)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if(response.isSuccessful()){
                return response;
            }else{
                ErrorResponse paymentErrorResponse = new Gson().fromJson(response.body().string(), ErrorResponse.class);
                throw new Exception(String.valueOf(paymentErrorResponse));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static PaymentResponse makePayment(
            String username,
            String destination,
            String memo,
            String amount,
            String secretKey,
            String baseUrl,
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
                    .url(baseUrl+"/v2/users/"+username+"/payments")
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
                ErrorResponse paymentErrorResponse = new Gson().fromJson(response.body().string(), ErrorResponse.class);
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
            String baseUrl,
            String assetIssuer,
            String assetCode
    ) throws IOException {
        Response response = confirmPaymentDetail(
                username,
                destination,
                memo,
                amount,
                secretKey,
                baseUrl,
                assetIssuer,
                assetCode,
                "",
                "",
                ""
        );
        PaymentResponse res = null;
        try {
            res = new Gson().fromJson(response.body().string(), PaymentResponse.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PaymentResponse res2 = makePayment(
                username,
                destination,
                memo,
                amount,
                secretKey,
                baseUrl,
                assetIssuer,
                assetCode,
                res
        );
        return res2;
    }

    public static Boolean createBudsInfo(
            String secretKey,
            String username,
            String email,
            String lastName,
            String firstName,
            String middleName,
            String mobile,
            String baseURL,
            String verificationCode,
            String captchaCode,
            String captchaChallenge,
            String referrer
    ) {

        try {
            KeyPair keyPairModel = KeyPair.fromSecretSeed(secretKey);
            String publicKey = keyPairModel.getAccountId();
            String secretSeed = new String(keyPairModel.getSecretSeed());

            OkHttpClient client = new OkHttpClient();
            CreateAccountRequest createAccount = CreateAccountRequest.getInstance(
                    username, email, lastName, firstName, middleName, mobile, captchaChallenge,
                    captchaCode, referrer, verificationCode
            );

            String encodedRequestBody = new Gson().toJson(createAccount);

            RequestBody body = RequestBody.create(JSON, encodedRequestBody);
            String signature = signHTTP("/v2/users", secretSeed, encodedRequestBody);
            Request request = new Request.Builder()
                    .url(baseURL+"/v2/users")
                    .post(body)
                    .addHeader("X-BANTUPAY-PUBLIC-KEY", publicKey)
                    .addHeader("X-BANTUPAY-SIGNATURE", signature)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if(response.isSuccessful()){
                return true;
            }else{
                ErrorResponse paymentErrorResponse = new Gson().fromJson(response.body().string(), ErrorResponse.class);
                throw new Exception(String.valueOf(paymentErrorResponse));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CreateAccountResponse verfifyBudsInfo(
            String secretKey,
            String username,
            String email,
            String lastName,
            String firstName,
            String middleName,
            String mobile,
            String baseURL,
            String verificationCode,
            String captchaCode,
            String captchaChallenge,
            String referrer
    ) {
        try {
            KeyPair keyPairModel = KeyPair.fromSecretSeed(secretKey);
            String publicKey = keyPairModel.getAccountId();
            String secretSeed = new String(keyPairModel.getSecretSeed());

            OkHttpClient client = new OkHttpClient();
            CreateAccountRequest createAccount = CreateAccountRequest.getInstance(
                    username, email, lastName, firstName, middleName, mobile, captchaChallenge,
                    captchaCode, referrer, verificationCode
            );

            String encodedRequestBody = new Gson().toJson(createAccount);

            RequestBody body = RequestBody.create(JSON, encodedRequestBody);
            String signature = signHTTP("/v2/users", secretSeed, encodedRequestBody);
            Request request = new Request.Builder()
                    .url(baseURL+"/v2/users")
                    .post(body)
                    .addHeader("X-BANTUPAY-PUBLIC-KEY", publicKey)
                    .addHeader("X-BANTUPAY-SIGNATURE", signature)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            System.out.println( "success?");
            System.out.println(response.body().string());
            System.out.println(response.isSuccessful());

            if(response.isSuccessful()){
                CreateAccountResponse res = new Gson().fromJson(response.body().string(), CreateAccountResponse.class);
                return res;
            }else{
                System.out.println("Error?...");
                ErrorResponse errorResponse = new Gson().fromJson(response.body().string(), ErrorResponse.class);
                throw new Exception(String.valueOf(errorResponse));
            }
        } catch (Exception e) {
            System.out.println("here we are.. broken...");
            e.printStackTrace();
        }
        return null;
    }
}