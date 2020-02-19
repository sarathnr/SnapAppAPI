package com.snapApp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.ribbon.proxy.annotation.Http;
import com.snapApp.api.users.data.UserEntity;
import com.snapApp.api.users.data.UserRepository;
import com.snapApp.api.users.shared.UserDTO;
import com.snapApp.api.users.ui.model.AlbumResponseModel;

@Service
public class UserServiceImpl implements UserService {

	public UserRepository userRepository;
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	public Environment env;
	//public RestTemplate restTemplate;
	AlbumServiceClient albumFeignClient;
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			Environment env,AlbumServiceClient albumFeignClient/*RestTemplate restTemplate*/) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.env = env;
		//this.restTemplate=restTemplate;
		this.albumFeignClient=albumFeignClient;
	}

	@Override
	public UserDTO createUser(UserDTO userDto) {

		ModelMapper mapper = new ModelMapper();

		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncyptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		UserEntity userEntitySaved = userRepository.save(userEntity);
		UserDTO createdUser = mapper.map(userEntitySaved, UserDTO.class);
		return createdUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new User(userEntity.getEmail(), userEntity.getEncyptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new ModelMapper().map(userEntity, UserDTO.class);
	}

	@Override
	public UserDTO getUserId(String userId) {


		UserEntity entity = userRepository.findByUserId(userId);
		UserDTO userDto = new ModelMapper().map(entity, UserDTO.class);
		/*
		 * String albumsUrl =String.format(env.getProperty("album.service.url"),userId);
		 * ResponseEntity<List<AlbumResponseModel>> albumsListResponse =
		 * restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new
		 * ParameterizedTypeReference<List<AlbumResponseModel>>() { });
    	 * userDto.setAlbumsListResponse(albumsListResponse.getBody());
		 */
		List<AlbumResponseModel> albumModel = albumFeignClient.getAlbums(userId);
		userDto.setAlbumsListResponse(albumModel);
		return userDto;

	}

}
