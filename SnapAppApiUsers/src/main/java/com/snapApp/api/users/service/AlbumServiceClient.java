package com.snapApp.api.users.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.snapApp.api.users.ui.model.AlbumResponseModel;

@FeignClient(name = "albums-ws",fallback=AlbumsServiceFallback.class)
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albumss")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}

@Component
class AlbumsServiceFallback implements AlbumServiceClient{

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		List<AlbumResponseModel> defaultAlbums = new ArrayList<>();
		AlbumResponseModel arm = new AlbumResponseModel();
		arm.setAlbumId("1234");
		arm.setUserId("default");
		arm.setName("default");
		arm.setDescription("default");
		defaultAlbums.add(arm);
		return defaultAlbums;
	}

}