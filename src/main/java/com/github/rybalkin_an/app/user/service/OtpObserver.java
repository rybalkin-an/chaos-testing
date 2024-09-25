package com.github.rybalkin_an.app.user.service;

public interface OtpObserver {
    void onOtpGenerated(String userId, String otp);
}
