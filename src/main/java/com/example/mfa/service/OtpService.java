package com.example.mfa.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateOtp(String username) {
        String otp = String.format("%06d", secureRandom.nextInt(1_000_000));
        otpStore.put(username, otp);
        return otp;
    }

    public boolean verifyOtp(String username, String otp) {
        String savedOtp = otpStore.get(username);
        boolean matched = savedOtp != null && savedOtp.equals(otp);
        if (matched) {
            otpStore.remove(username);
        }
        return matched;
    }

    public void setOtpForTesting(String username, String otp) {
        otpStore.put(username, otp);
    }
}
