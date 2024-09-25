package com.github.rybalkin_an.app.user.service.impl;

import com.github.rybalkin_an.app.user.service.TwoFactorAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwoFactorAuthenticationServiceImpl implements TwoFactorAuthenticationService {

    private final Map<String, String> userOtpStore = new HashMap<>();
    private final Random random = new Random();
    private final OtpNotificationSubject notificationSubject;

    @Autowired
    public TwoFactorAuthenticationServiceImpl(OtpNotificationSubject notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    @Override
    public String generateOTP(String userId) {
        String otp = String.format("%06d", random.nextInt(999999));

        userOtpStore.put(userId, otp);

        notificationSubject.notifyObservers(userId, otp);

        return otp;
    }

    @Override
    public boolean validateOTP(String userId, String otp) {
        return otp.equals(userOtpStore.get(userId));
    }

    @Override
    public void clearOTP(String userId) {
        userOtpStore.remove(userId);
    }
}
