package com.masai.Service;

import com.masai.Entity.Users;
import com.masai.Exception.UserException;

public interface UserService {
	 Users registerUser(Users users);
	 Users updateUser(int userId, Users users) throws UserException;
	 void deleteUser(int userId) throws UserException;
	 Users getUserById(int userId) throws UserException;
	 Users getUserByEmail(String username) throws UserException;
	 boolean authenticateUser(String email, String password);
}
