package in.com.furniturebackend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.com.furniturebackend.model.Otp;
import in.com.furniturebackend.model.User;
import in.com.furniturebackend.repository.OtpRepository;
import in.com.furniturebackend.repository.UserRepository;
import in.com.furniturebackend.security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {



    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 🔹 OTP Login / Register
//    @Override
//    public User loginOrRegister(String mobileNumber, String email, String password) {
//
//        Optional<User> optionalUser = userRepo.findByMobileNumber(mobileNumber);
//
//        // Agar user exist karta hai → return user
//        if (optionalUser.isPresent()) {
//            return optionalUser.get();
//        }
//
//        // New registration case
//        if (email == null || password == null ||
//            email.isEmpty() || password.isEmpty()) {
//            throw new RuntimeException("Email and Password required for registration");
//        }
//
//        User user = new User();
//        user.setMobileNumber(mobileNumber);
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole("ROLE_USER");
//        user.setActive(true);
//
//        return userRepo.save(user);
//    }
    
    

    // 🔹 Email + Password login
    @Override
    public String loginWithPassword(String email, String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getMobileNumber(), user.getRole());
    }

    // 🔹 Mobile + Password login
    @Override
    public String loginWithMobileAndPassword(String mobileNumber, String password) {

        User user = userRepo.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepo.save(user);

        return jwtUtil.generateToken(user.getMobileNumber(), user.getRole());
    }

    // 🔹 Forgot Password
    @Override
    public User forgotPassword(String mobileNumber, String otp, String newPassword) {

        User user = userRepo.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setFailedLoginAttempts(0);

        return userRepo.save(user);
    }
    
//    @Override
//    public User register(String mobileNumber, String email, String password) {
//
//        if (userRepo.findByMobileNumber(mobileNumber).isPresent()) {
//            throw new RuntimeException("User already exists");
//        }
//
//        User user = new User();
//        user.setMobileNumber(mobileNumber);
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole("ROLE_USER");
//        user.setActive(true);
//
//        return userRepo.save(user);
//    }
    
    @Override
    public User register(String mobileNumber, String email, String password) {

        if (userRepo.findByMobileNumber(mobileNumber).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setMobileNumber(mobileNumber);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        user.setActive(true);

        return userRepo.save(user);
    }
    
//    @Override
//    public String generateTokenAfterOtp(String mobileNumber) {
//
//        User user = userRepo.findByMobileNumber(mobileNumber)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return jwtUtil.generateToken(user.getMobileNumber(), user.getRole());
//    }
    
    @Override
    public String generateTokenAfterOtp(String mobileNumber) {

        User user = userRepo.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not registered. Please register first."));

        return jwtUtil.generateToken(user.getMobileNumber(), user.getRole());
    }
    
    @Override
    public void resetPassword(String mobileNumber, String newPassword) {

        User user = userRepo.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setFailedLoginAttempts(0);

        userRepo.save(user);
    }
    
    public User findByMobileNumber(String mobile) {
        return userRepo.findByMobileNumber(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}