package com.dash_lat_net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DashLatNetBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashLatNetBackApplication.class, args);
	}

}
