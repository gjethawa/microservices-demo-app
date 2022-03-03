package com.zensar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	//Comment RESTTEMPLATE if using OPEN FEIGN - @EnableFeignClients
	/*
	@Bean // whenever my applicaton is launched, it will create a bean of restTemplate. you can use this oject in controller after that.
	@LoadBalanced //what does this do ? added this to resolve UNKNOWN HOST EXCEPTION
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}*/
}
