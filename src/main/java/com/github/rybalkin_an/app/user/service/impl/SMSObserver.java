package com.github.rybalkin_an.app.user.service.impl;

import com.github.rybalkin_an.app.user.service.OtpObserver;
import org.springframework.stereotype.Component;

@Component
public class SMSObserver implements OtpObserver {
    @Override
    public void onOtpGenerated(String userId, String otp) {
        // Logic to send OTP via SMS
        System.out.println("Sending OTP via SMS to user: " + userId + " OTP: " + otp);
    }
}
