package in.com.furniturebackend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import in.com.furniturebackend.exception.BadRequestException;
import in.com.furniturebackend.exception.ResourceNotFoundException;
import in.com.furniturebackend.model.User;
import in.com.furniturebackend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User createUser(User user) {
		
		// Check if mobile number already exists
        userRepo.findByMobileNumber(user.getMobileNumber())
            .ifPresent(u -> {
                throw new BadRequestException("Mobile number already exists! Please use another.");
            });
		
		return userRepo.save(user);
	}
	
	@Override
	public User getUserById(Long id) {
		return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found id"+id));
	}
	
	@Override
	public User findByMobileNumber(String mobile) {
	    return userRepo.findByMobileNumber(mobile)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	@Override
	public List<User> getAllUsers() {
	    return userRepo.findAll();
	}
	
	
}
