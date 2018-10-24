package com.stream.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	private MessageChannel messageChannel;

	private OrderChannelConfig streamsConfig;

	public OrderController(OrderChannelConfig streamsConfig) {
		this.streamsConfig = streamsConfig;
	}

	@GetMapping(path = "/message")
	public ResponseEntity<String> createOrder() {

		this.messageChannel = streamsConfig.streamsOutput();
		boolean messageSent = messageChannel
				.send(MessageBuilder.withPayload(new OrderCreatedEvent("Order Created")).build());
		System.out.println("Message Sent : " + messageSent);
		return new ResponseEntity<String>("Order Created", HttpStatus.OK);
	}

}
