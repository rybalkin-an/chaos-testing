package com.github.rybalkin_an.app.user.service;

public interface UserNotificationService {

    String sendEmailNotification(String email, String subject, String message);

    String notifyUserOfRegistration(String email);

    String notifyUserOfPasswordChange(String email);
}
