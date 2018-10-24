package com.stream.controller;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OrderChannelConfig {
	String INPUT = "sin";
	String OUTPUT = "sout";

	@Input(INPUT)
	KStream<String, OrderCreatedEvent> getStreamsInput();

	@Output(OUTPUT)
	MessageChannel streamsOutput();

}
