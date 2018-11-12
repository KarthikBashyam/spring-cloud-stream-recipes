package com.stream.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	private MessageChannel messageChannel;

	private OrderChannelConfig streamsConfig;

	@Autowired
	private QueryableStoreRegistry queryableStoreRegistry;

	public OrderController(OrderChannelConfig streamsConfig) {
		this.streamsConfig = streamsConfig;
	}

	@GetMapping(path = "/message")
	public ResponseEntity<String> createOrder() {

		this.messageChannel = streamsConfig.streamsOutput();
		boolean messageSent = messageChannel.send(MessageBuilder.withPayload("Order Created")
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
		System.out.println("Message Sent : " + messageSent);
		return new ResponseEntity<String>("Order Created", HttpStatus.OK);
	}

	@GetMapping(path = "/keystore")
	public String kafkaKeyStore() {
		
		ReadOnlyKeyValueStore<String, Long> messageCount = queryableStoreRegistry.getQueryableStoreType("message-count",
				QueryableStoreTypes.keyValueStore());
		
		KeyValueIterator<String, Long> messages = messageCount.all();

		Map<String, Long> values = new HashMap<>();
		while (messages.hasNext()) {
			KeyValue<String, Long> message = messages.next();
			values.put(message.key, message.value);
		}
		
		return values.toString();
	}

}
