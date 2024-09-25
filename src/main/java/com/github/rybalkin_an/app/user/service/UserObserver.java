package com.github.rybalkin_an.app.user.service;

public interface UserObserver {
    void notify(String email, String subject, String message);
}
