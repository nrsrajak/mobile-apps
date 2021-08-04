package com.mobile.app.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobile.app.io.entity.UserEntity;
import com.mobile.app.io.repositories.UserRepository;
import com.mobile.app.service.UserService;
import com.mobile.app.shared.dto.UserDTO;
import com.mobile.app.util.UserUtils;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserUtils userUtil;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDTO createUser(UserDTO user) {

//		User validation if exests
		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("Record already exists!");
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		String publicGeneratedUserId = userUtil.generatedUserId(30);
		userEntity.setUserId(publicGeneratedUserId);

		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDTO returnValues = new UserDTO();
		BeanUtils.copyProperties(storedUserDetails, returnValues);
		return returnValues;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDTO getUserDetailsByUserId(String userId) {
		UserDTO userDTO = new UserDTO();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null) throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity, userDTO);
		return userDTO;
	}

}
