package com.github.rybalkin_an.app.user.service.impl;

import com.github.rybalkin_an.app.user.service.OtpObserver;
import com.github.rybalkin_an.app.user.service.UserObserver;
import org.springframework.stereotype.Component;

@Component
public class EmailObserver implements UserObserver, OtpObserver {
    @Override
    public void notify(String email, String subject, String message) {
        System.out.println("Email sent to " + email + " with subject: " + subject + " and message: " + message);
    }

    @Override
    public void onOtpGenerated(String userId, String otp) {
        // Logic to send OTP via email
        System.out.println("Sending OTP via email to user: " + userId + " OTP: " + otp);
    }
}
