package com.mobile.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mobile.app.shared.dto.UserDTO;

public interface UserService extends UserDetailsService{
	UserDTO createUser(UserDTO user);
	UserDTO getUser(String email);

    UserDTO getUserDetailsByUserId(String id);
}
