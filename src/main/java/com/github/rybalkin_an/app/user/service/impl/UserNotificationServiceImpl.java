package com.github.rybalkin_an.app.user.service.impl;

import com.github.rybalkin_an.app.user.service.UserNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    private final UserNotificationSubject notificationSubject;

    @Autowired
    public UserNotificationServiceImpl(UserNotificationSubject notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    @Override
    public String sendEmailNotification(String email, String subject, String message) {
        notificationSubject.notifyObservers(email, subject, message);
        return "Notification sent to " + email + " with subject: " + subject;
    }

    @Override
    public String notifyUserOfRegistration(String email) {
        String subject = "Welcome to our service!";
        String message = "Thank you for registering with us!";
        sendEmailNotification(email, subject, message);
        return "Registration notification sent to " + email;
    }

    @Override
    public String notifyUserOfPasswordChange(String email) {
        String subject = "Your password has been changed";
        String message = "Your password was successfully changed. If this was not you, please contact support.";
        sendEmailNotification(email, subject, message);
        return "Password change notification sent to " + email;
    }
}
