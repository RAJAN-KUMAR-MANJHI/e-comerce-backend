package in.com.furniturebackend.service;

import in.com.furniturebackend.model.User;

public interface AuthService {

	// OTP login / register
//    User loginOrRegister(String mobileNumber, String email, String password);
    

    // Password login
    String loginWithPassword(String email, String password);

    // New: Mobile + Password login
    String loginWithMobileAndPassword(String mobileNumber, String password);

    //---new 
    
    // New: Forgot password
    User forgotPassword(String mobileNumber, String otp, String newPassword);
    
   
    String generateTokenAfterOtp(String mobileNumber);
    void resetPassword(String mobileNumber, String newPassword);
    
    User register(String mobileNumber, String email, String password);
 // ✅ ADD THIS (IMPORTANT)
    User findByMobileNumber(String mobileNumber);
    
}
