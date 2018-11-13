package com.stream.service;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.stream.controller.OrderChannelConfig;

@SpringBootApplication(scanBasePackages = "com.stream.*")
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

	@Bean
	CommandLineRunner sendMessages(OrderChannelConfig config) {

		return args -> {
			Runnable runnable = () -> {
			MessageChannel channel = config.streamsOutput();
			channel.send(MessageBuilder.withPayload("alice@hotmail.com")
					.setHeader(KafkaHeaders.MESSAGE_KEY, "alice".getBytes()).build());
			};
			Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.MINUTES);
		};
	}
}
