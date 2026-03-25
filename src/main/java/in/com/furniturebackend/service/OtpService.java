package in.com.furniturebackend.service;

import in.com.furniturebackend.model.User;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import in.com.furniturebackend.security.JwtUtil;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.com.furniturebackend.model.Otp;
import in.com.furniturebackend.repository.OtpRepository;
import in.com.furniturebackend.repository.UserRepository;

@Service
@Transactional
public class OtpService {

	 private final OtpRepository otpRepository;

	    public OtpService(OtpRepository otpRepository) {
	        this.otpRepository = otpRepository;
	    }

	    public String generateOtp(String mobileNumber) {

	        otpRepository.deleteByMobileNumber(mobileNumber);
	        otpRepository.flush();

	        String otpCode = String.format("%06d",
	                new Random().nextInt(900000) + 100000);

	        otpRepository.save(
	                new Otp(mobileNumber, otpCode,
	                        LocalDateTime.now().plusMinutes(5))
	        );

	        return otpCode;
	    }

	    public void verifyOtp(String mobileNumber, String code) {

	        Otp otp = otpRepository
	                .findTopByMobileNumberOrderByIdDesc(mobileNumber)
	                .orElseThrow(() -> new RuntimeException("OTP not found"));

	        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
	            otpRepository.deleteByMobileNumber(mobileNumber);
	            throw new RuntimeException("OTP expired");
	        }

	        if (!otp.getCode().equals(code)) {
	            throw new RuntimeException("Invalid OTP");
	        }

	        otpRepository.deleteByMobileNumber(mobileNumber);
	    }
	
	}