package in.com.furniturebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.com.furniturebackend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User>findByMobileNumber(String mobileNumber);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email); 
	
	
	//Optional<User> findByMobile(String mobile);
	
	
	// New method for duplicate mobile check
    boolean existsByMobileNumber(String mobileNumber);

}
