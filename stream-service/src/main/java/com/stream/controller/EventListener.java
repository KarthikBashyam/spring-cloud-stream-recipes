package com.stream.controller;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
	
	@StreamListener
	public void writeEvent(@Input(OrderChannelConfig.INPUT) KStream<String, OrderCreatedEvent> event) {
		System.out.println("====== EVENT LISTENER :" + event.mapValues(v -> v.getName()));
	}

}
