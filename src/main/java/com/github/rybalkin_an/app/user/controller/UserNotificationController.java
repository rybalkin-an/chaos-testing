package com.github.rybalkin_an.app.user.controller;

import com.github.rybalkin_an.app.user.service.impl.UserNotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class UserNotificationController {

    @Autowired
    private UserNotificationServiceImpl userNotificationService;

    @PostMapping("/register")
    public ResponseEntity<String> notifyUserOnRegistration(@RequestParam String email) {
        String response = userNotificationService.notifyUserOfRegistration(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-change")
    public ResponseEntity<String> notifyUserOnPasswordChange(@RequestParam String email) {
        String response = userNotificationService.notifyUserOfPasswordChange(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/custom")
    public ResponseEntity<String> sendCustomNotification(@RequestParam String email,
                                                         @RequestParam String subject,
                                                         @RequestParam String message) {
        String response = userNotificationService.sendEmailNotification(email, subject, message);
        return ResponseEntity.ok(response);
    }

}
