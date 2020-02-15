package com.snapApp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.Entity;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapApp.api.users.data.UserEntity;
import com.snapApp.api.users.data.UserRepository;
import com.snapApp.api.users.shared.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	public UserRepository userRepository;
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public UserDTO createUser(UserDTO userDto) {
		
		ModelMapper mapper = new ModelMapper();

		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncyptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto,UserEntity.class);
		UserEntity userEntitySaved = userRepository.save(userEntity);
		UserDTO createdUser = mapper.map(userEntitySaved,UserDTO.class);
		return createdUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		UserEntity userEntity = userRepository.findByEmail(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(), userEntity.getEncyptedPassword(), true, true, true, true, new ArrayList<>());
	}

	
	@Override
	public UserDTO getUserDetailsByEmail(String username) throws UsernameNotFoundException {
	
		UserEntity userEntity = userRepository.findByEmail(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new ModelMapper().map(userEntity, UserDTO.class);
	}
}
