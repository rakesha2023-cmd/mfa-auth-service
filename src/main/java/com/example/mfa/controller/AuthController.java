package com.example.mfa.controller;

import com.example.mfa.dto.ApiResponse;
import com.example.mfa.dto.LoginRequest;
import com.example.mfa.dto.OtpVerificationRequest;
import com.example.mfa.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            String otp = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                    "message", "Password verified. OTP generated successfully.",
                    "success", true,
                    "otp", otp
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(ex.getMessage(), false));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        try {
            authService.verifyOtp(request.getUsername(), request.getOtp());
            return ResponseEntity.ok(new ApiResponse("Authentication successful", true));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(ex.getMessage(), false));
        }
    }
}
