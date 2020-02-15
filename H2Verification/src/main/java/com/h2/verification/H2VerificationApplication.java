package com.h2.verification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class H2VerificationApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(H2VerificationApplication.class, args);
	}

}
