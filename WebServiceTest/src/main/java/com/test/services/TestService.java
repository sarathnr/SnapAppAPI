package com.test.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestService {

	@GetMapping("/getstatus")
	public Map status() {
		Map m = new HashMap();
		m.put("Name", "Sarath");
		return m;
	}

}
