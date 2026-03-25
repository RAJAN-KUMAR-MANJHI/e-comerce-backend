package in.com.furniturebackend.dto;

import lombok.Data;

@Data
public class LoginRequest {
	
	// Changed from email to mobileNumber
    private String mobileNumber;

    private String password;

    //--getters & setters
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
