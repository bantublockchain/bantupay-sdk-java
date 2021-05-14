package com.bantupay.sdk;

public class CreateAccountRequest {
    private String username;
    private String email;
    private String verificationCode;
    private String lastName;
    private String firstName;
    private String middleName;
    private String mobile;
    private String captchaChallenge;
    private String captchaCode;
    private String referrer;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCaptchaChallenge() {
        return captchaChallenge;
    }

    public void setCaptchaChallenge(String captchaChallenge) {
        this.captchaChallenge = captchaChallenge;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }


    public static CreateAccountRequest getInstance(
            String username,
            String email,
            String lastName,
            String firstName,
            String middleName,
            String mobile,
            String captchaCode,
            String captchaChallenge,
            String referrer,
            String verificationCode
    ) {
        CreateAccountRequest accountRequest = new CreateAccountRequest();
        accountRequest.username = username;
        accountRequest.email = email;
        accountRequest.lastName = lastName;
        accountRequest.firstName = firstName;
        accountRequest.middleName = middleName;
        accountRequest.mobile = mobile;
        accountRequest.captchaCode = captchaCode;
        accountRequest.captchaChallenge = captchaChallenge;
        accountRequest.referrer = referrer;
        accountRequest.verificationCode = verificationCode;

        return accountRequest;
    }

    @Override
    public String toString() {
        return "";
    }
}
