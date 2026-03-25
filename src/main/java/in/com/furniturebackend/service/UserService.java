package in.com.furniturebackend.service;

import java.util.List;

import in.com.furniturebackend.model.User;

public interface UserService {
	
	User createUser(User user);
	User getUserById(Long id);
	
	User findByMobileNumber(String mobile);

	List<User> getAllUsers();
}
