package com.masai.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	    public ResponseEntity<?> userLogin(Authentication auth) {
	        try {
	            if (auth != null) {
	                Users user = userService.getUserByEmail(auth.getName());
	                return new ResponseEntity<Users>(user, HttpStatus.OK);
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
	            }
	        } catch (UserException e) {
	            logger.error("Error during user login: " + e.getMessage());
	            return ResponseEntity.badRequest().body("Login failed. " + e.getMessage());
	        } catch (Exception e) {
	            logger.error("Error during user login: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed due to an internal error.");
	        }
	    }
}
