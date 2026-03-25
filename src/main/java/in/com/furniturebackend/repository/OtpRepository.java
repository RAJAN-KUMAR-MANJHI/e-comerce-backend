package in.com.furniturebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import in.com.furniturebackend.model.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long>{
	
//	Optional<Otp>findTopByMobileNumberOrderByIdDesc(String mobileNumber);
//	
//	@Modifying
//	@Transactional
//	 @Query("DELETE FROM Otp o WHERE o.mobileNumber = :mobile")
//    void deleteByMobileNumber(@Param("mobile") String mobileNumber);
			
	   Optional<Otp> findByMobileNumber(String mobileNumber);
//
//	    void deleteByMobileNumber(String mobileNumber);
	    

	    Optional<Otp> findTopByMobileNumberOrderByIdDesc(String mobileNumber);

	    void deleteByMobileNumber(String mobileNumber);

}
