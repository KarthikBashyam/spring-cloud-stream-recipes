package com.stream.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import com.stream.controller.OrderChannelConfig;

@SpringBootApplication(scanBasePackages="com.stream.*")
@EnableBinding(OrderChannelConfig.class)
public class StreamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner startup() {
		return args -> {
			System.out.println("========= CLOUD STREAM APPLICATION STARTED ============");
		};

	}
}
