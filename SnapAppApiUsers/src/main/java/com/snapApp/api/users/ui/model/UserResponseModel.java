package com.snapApp.api.users.ui.model;

import java.util.List;

import com.snapApp.api.users.ui.model.AlbumResponseModel;

public class UserResponseModel {

	private String userId;
	private String firstName;

	private String lastName;
	private String email;
	private String password;
	private String encyptedPassword;
	List<AlbumResponseModel> albumsListResponse;

	public String getEncyptedPassword() {
		return encyptedPassword;
	}

	public List<AlbumResponseModel> getAlbumsListResponse() {
		return albumsListResponse;
	}

	public void setAlbumsListResponse(List<AlbumResponseModel> albumsListResponse) {
		this.albumsListResponse = albumsListResponse;
	}

	public void setEncyptedPassword(String encyptedPassword) {
		this.encyptedPassword = encyptedPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
