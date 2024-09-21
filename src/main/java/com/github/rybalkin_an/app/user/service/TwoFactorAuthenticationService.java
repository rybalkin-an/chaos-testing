package com.github.rybalkin_an.app.user.service;

public interface TwoFactorAuthenticationService {

    String generateOTP(String userId);

    boolean validateOTP(String userId, String otp);

    void clearOTP(String userId);
}
