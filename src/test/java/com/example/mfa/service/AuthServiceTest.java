package com.example.mfa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceTest {
    private AuthService authService;
    private OtpService otpService;

    @BeforeEach
    void setUp() {
        UserService userService = new UserService();
        otpService = new OtpService();
        authService = new AuthService(userService, otpService);
    }

    @Test
    void shouldGenerateOtpForValidCredentials() {
        String otp = authService.login("rakesh", "Password@123");
        assertNotNull(otp);
        assertEquals(6, otp.length());
    }

    @Test
    void shouldRejectInvalidPassword() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> authService.login("rakesh", "wrong"));
        assertEquals("Invalid username or password", ex.getMessage());
    }

    @Test
    void shouldRejectUnknownUserDuringOtpVerification() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> authService.verifyOtp("ghost", "123456"));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void shouldRejectInvalidOtp() {
        authService.login("rakesh", "Password@123");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> authService.verifyOtp("rakesh", "000000"));
        assertEquals("Invalid OTP", ex.getMessage());
    }

    @Test
    void shouldAuthenticateWithValidOtp() {
        otpService.setOtpForTesting("rakesh", "123456");
        assertDoesNotThrow(() -> authService.verifyOtp("rakesh", "123456"));
    }
}
