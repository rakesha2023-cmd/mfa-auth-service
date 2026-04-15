package com.example.mfa.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final OtpService otpService;

    public AuthService(UserService userService, OtpService otpService) {
        this.userService = userService;
        this.otpService = otpService;
    }

    public String login(String username, String password) {
        if (!userService.isValidUser(username, password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return otpService.generateOtp(username);
    }

    public void verifyOtp(String username, String otp) {
        if (!userService.exists(username)) {
            throw new IllegalArgumentException("User not found");
        }
        if (!otpService.verifyOtp(username, otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }
}
