package com.stream.service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;

import com.stream.controller.OrderChannelConfig;
import com.stream.dto.Product;
import com.stream.dto.ProductSales;

@SpringBootApplication(scanBasePackages = "com.stream.*")
@EnableBinding(OrderChannelConfig.class)
public class StreamServiceApplication {

	private static final Log LOGGER = LogFactory.getLog(StreamServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StreamServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner startup() {
		return args -> {
			LOGGER.info("========= CLOUD STREAM APPLICATION STARTED ============");
		};
	}

	@Bean
	@ConditionalOnProperty(name = "stream.topic", havingValue = "messages")
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

	@Bean
	@ConditionalOnProperty(name = "stream.topic", havingValue = "products")
	CommandLineRunner sendProductMessages(OrderChannelConfig config) {
		return args -> {

			Runnable runnable = () -> {
			
			MessageChannel channel = config.productStreamOutput();
			Product product = new Product("IPHONE", "Apple IPhone");

			channel.send(MessageBuilder.withPayload(product).setHeader(KafkaHeaders.MESSAGE_KEY, "IPHONE".getBytes())
					.build());
			LOGGER.info("Message sent to Product Topic!!");
			};
			Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);

		};
	}

	@Bean
	@ConditionalOnProperty(name = "stream.topic", havingValue = "products")
	CommandLineRunner sendProductSalesMessages(OrderChannelConfig config) {

		return args -> {

			Runnable runnable = () -> {

				MessageChannel channel = config.productSalesStreamOutput();
				ProductSales productSales = new ProductSales("Walmart", "IPHONE", 150L);

				channel.send(MessageBuilder.withPayload(productSales)
						.setHeader(KafkaHeaders.MESSAGE_KEY, "IPHONE".getBytes()).build());
				LOGGER.info("Message sent to Product Sales Topic!!");
			};

			Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);

		};
	}

}
