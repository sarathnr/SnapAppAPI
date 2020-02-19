package com.snapApp.api.users.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.snapApp.api.users.ui.model.AlbumResponseModel;

@FeignClient(name = "albums-ws")
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
