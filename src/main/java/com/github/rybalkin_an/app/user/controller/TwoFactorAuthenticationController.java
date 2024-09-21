package com.github.rybalkin_an.app.user.controller;

import com.github.rybalkin_an.app.user.service.impl.TwoFactorAuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/twofa")
public class TwoFactorAuthenticationController {

    @Autowired
    private TwoFactorAuthenticationServiceImpl twoFactorAuthenticationService;

    @PostMapping("/generate/{userId}")
    public ResponseEntity<String> generateOtp(@PathVariable String userId) {
        String otp = twoFactorAuthenticationService.generateOTP(userId);
        return ResponseEntity.ok("OTP generated: " + otp);
    }

    @PostMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateOtp(@PathVariable String userId, @RequestParam String otp) {
        boolean isValid = twoFactorAuthenticationService.validateOTP(userId, otp);
        return ResponseEntity.ok(isValid);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearOtp(@PathVariable String userId) {
        twoFactorAuthenticationService.clearOTP(userId);
        return ResponseEntity.ok("OTP cleared for userId: " + userId);
    }

}
