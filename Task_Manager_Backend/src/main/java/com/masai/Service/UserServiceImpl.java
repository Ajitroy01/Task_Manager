package com.masai.Service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.masai.Entity.Users;
import com.masai.Exception.UserException;
import com.masai.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private final CustomUserDetailsService customUserDetailsService;
	
	public UserServiceImpl(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
	
    @Autowired
    private UserRepository userRepository;

    @Override
    public Users registerUser(Users users) throws UserException {
        // Validate the user entity using Bean Validation
        // Jakarta Validation annotations will enforce constraints
        if (users == null) {
            throw new UserException("User data is null.");
        }
        // Save the user to the database
        return userRepository.save(users);
    }

    @Override
    public Users updateUser(int userId, Users updatedUser) throws UserException {
        // Check if the user with the given ID exists
        Users existingUser = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        
        // Update the existing user with the new data
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        // ... update other fields as needed
        
        // Save the updated user
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(int userId) throws UserException {
        // Check if the user with the given ID exists
        Users existingUser = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));

        // Delete the user
        userRepository.delete(existingUser);
    }

    @Override
    public Users getUserById(int userId) throws UserException {
        // Check if the user with the given ID exists
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public Users getUserByEmail(String email) throws UserException {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserException("User not found with email: " + email);
        }
    }
    
}
