package in.com.furniturebackend.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import in.com.furniturebackend.dto.LoginRequest;
import in.com.furniturebackend.dto.OtpRequest;
import in.com.furniturebackend.exception.BadRequestException;
import in.com.furniturebackend.model.User;
import in.com.furniturebackend.security.JwtUtil;
import in.com.furniturebackend.service.AuthService;
import in.com.furniturebackend.service.OtpService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final OtpService otpService;
    private final AuthService authService;

    public AuthController(OtpService otpService,
                          AuthService authService) {
        this.otpService = otpService;
        this.authService = authService;
    }

    // 1️⃣ Send OTP
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest request) {

        String otp = otpService.generateOtp(request.getMobileNumber());

        return ResponseEntity.ok(Map.of(
                "message", "OTP sent successfully"
                // ❌ Production me OTP return nahi karna
        ));
    }

    // 2️⃣ Verify OTP (Login)
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {

        otpService.verifyOtp(
                request.getMobileNumber(),
                request.getOtp()
        );

        return ResponseEntity.ok(Map.of(
                "message", "OTP verified successfully"
        ));
    }
    


    // 3️⃣ Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody OtpRequest request) {

        User user = authService.register(
                request.getMobileNumber(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(Map.of(
                "message", "Registration successful",
                "userId", user.getId()
        ));
    }

    // 4️⃣ Login with Password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String token = authService
                .loginWithMobileAndPassword(
                        request.getMobileNumber(),
                        request.getPassword()
                );
        // ✅ user fetch karo
        User user = authService.findByMobileNumber(request.getMobileNumber());

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "role", user.getRole(),
                "id", user.getId()
        ));
    }

    // 5️⃣ Forgot Password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody OtpRequest request) {

        otpService.verifyOtp(
                request.getMobileNumber(),
                request.getOtp()
        );

        authService.resetPassword(
                request.getMobileNumber(),
                request.getPassword()
        );

        return ResponseEntity.ok("Password reset successful");
    }
}