package com.openwt.boat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class BoatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoatApplication.class, args);
	}

}
