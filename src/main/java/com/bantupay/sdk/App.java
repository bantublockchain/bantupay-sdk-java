package com.bantupay.sdk;// create a completely new and unique pair of keys.
// see more about KeyPair objects: https://stellar.github.io/java-stellar-sdk/org/stellar/sdk/KeyPair.html
import okhttp3.*;
import org.stellar.sdk.KeyPair;
import shadow.com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

    public static String signTxt (String secretSeed, String transactionXDR, String networkPhrase) {

        return "hi";
    }

    public static void main (String[] args) {
//        JsonObject obj = new JsonObject();
//        System.out.println(signHTTP("/v2/user/", "SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI"));
        makeAPICall();
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static void makeAPICall(){
        try {
            KeyPairModel keyPairModel = createAccount();
            String publicKey = keyPairModel.getAccountId();

            OkHttpClient client = new OkHttpClient();
            PaymentRequest paymentRequest = PaymentRequest.getInstance("proxie", "thundeyy", "test payment", "0.001");
            String encodedRequestBody = new Gson().toJson(paymentRequest);
            RequestBody body = RequestBody.create(JSON, encodedRequestBody);

            String signature = signHTTP("/v2/users/proxie/payments", keyPairModel.getSecretSeed(), encodedRequestBody);
            Request request = new Request.Builder()
                    .url("https://api.bantupay.org/v2/users/proxie/payments")
                    .post(body)
                    .addHeader("X-BANTUPAY-PUBLIC-KEY", publicKey)
                    .addHeader("X-BANTUPAY-SIGNATURE", signature)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if(response.isSuccessful()){
                PaymentResponse paymentResponse = new Gson().fromJson(response.body().string(), PaymentResponse.class);
                System.out.println("Successful call: " + paymentResponse.toString());
            }else{
                PaymentErrorResponse paymentErrorResponse = new Gson().fromJson(response.body().string(), PaymentErrorResponse.class);
                System.out.println("Error: " +  paymentErrorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}