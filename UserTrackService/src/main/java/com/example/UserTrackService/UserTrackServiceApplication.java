package com.example.UserTrackService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class UserTrackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserTrackServiceApplication.class, args);
	}

}
