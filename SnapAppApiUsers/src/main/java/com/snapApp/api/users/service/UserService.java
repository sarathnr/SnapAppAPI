package com.snapApp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.snapApp.api.users.shared.UserDTO;

public interface UserService  extends UserDetailsService{
	
	UserDTO createUser(UserDTO userDto);
	UserDTO getUserDetailsByEmail(String username);

}
