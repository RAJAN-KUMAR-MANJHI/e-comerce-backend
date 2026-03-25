package in.com.furniturebackend.dto;

public class OtpRequest {

	 private String mobileNumber;
	    private String otp;
	    private String email; // optional: for new user registration
	    private String password;

	
		//-- getters & setters
	    public String getMobileNumber() { return mobileNumber; }
	    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

	    public String getOtp() { return otp; }
	    public void setOtp(String otp) { this.otp = otp; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	    
	    public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}

}
