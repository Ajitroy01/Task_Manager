package com.masai.Service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.Entity.Users;
import com.masai.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Optional<Users> opt = userRepo.findByEmail(email);

	    if (opt.isPresent()) {
	        Users user = opt.get();

	        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	    } else {
	        throw new UsernameNotFoundException("User Details not found with this email: " + email);
	    }
	}

}
