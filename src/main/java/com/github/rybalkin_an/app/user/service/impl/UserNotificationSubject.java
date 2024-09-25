package com.github.rybalkin_an.app.user.service.impl;

import com.github.rybalkin_an.app.user.service.UserObserver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserNotificationSubject {
    private final List<UserObserver> userObservers = new ArrayList<>();

    public void registerObserver(UserObserver observer) {
        userObservers.add(observer);
    }

    public void unregisterObserver(UserObserver observer) {
        userObservers.remove(observer);
    }

    public void notifyObservers(String email, String subject, String message) {
        for (UserObserver observer : userObservers) {
            observer.notify(email, subject, message);
        }
    }
}
