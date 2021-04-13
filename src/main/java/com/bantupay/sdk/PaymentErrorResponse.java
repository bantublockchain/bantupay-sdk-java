package com.bantupay.sdk;

public class PaymentErrorResponse {
    private String data;
    private String error;
    private String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PaymentErrorResponse{" +
                "data='" + data + '\'' +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
