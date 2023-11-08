package com.masai.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.Entity.Users;
import com.masai.Exception.UserException;
import com.masai.Service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody Users user) {
	    try {
	        // Validate and register the user
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        Users registeredUser = userService.registerUser(user);
	        logger.info("User registered successfully: " + user.getName());
	        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
	    } catch (UserException e) {
	        logger.error("Error while registering user: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	}
	 
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
	    try {
	        // Authenticate the user
	        boolean isAuthenticated = userService.authenticateUser(email, password);

	        if (isAuthenticated) {
	            // Retrieve the authenticated user details
	            Users authenticatedUser = userService.getUserByEmail(email);

	            if (authenticatedUser != null) {
	                int userId = authenticatedUser.getId();
	                String userEmail = authenticatedUser.getEmail();

	                logger.info("User logged in successfully.");
	                return ResponseEntity.ok("{\"userId\": " + userId + ", \"email\": \"" + userEmail + "\"}");
	            } else {
	                logger.error("User not found.");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	            }
	        } else {
	            logger.error("Login failed for user.");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
	        }
	    } catch (UserException e) {
	        logger.error("Error while logging in: " + e.getMessage());
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
}
