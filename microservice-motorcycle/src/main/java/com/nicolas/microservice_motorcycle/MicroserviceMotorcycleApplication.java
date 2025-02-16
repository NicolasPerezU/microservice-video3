package com.nicolas.microservice_motorcycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceMotorcycleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceMotorcycleApplication.class, args);
	}

}
