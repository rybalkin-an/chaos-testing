package com.github.rybalkin_an.app.user.service.impl;

import com.github.rybalkin_an.app.user.service.OtpObserver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OtpNotificationSubject {

    private final List<OtpObserver> otpObservers = new ArrayList<>();

    public void registerObserver(OtpObserver observer) {
        otpObservers.add(observer);
    }

    public void unregisterObserver(OtpObserver observer) {
        otpObservers.remove(observer);
    }

    public void notifyObservers(String userId, String otp) {
        for (OtpObserver observer : otpObservers) {
            observer.onOtpGenerated(userId, otp);
        }
    }
}
