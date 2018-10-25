package com.stream.controller;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Kafka Binding
 * 
 * @author Karthik
 *
 */
public interface OrderChannelConfig {
	String INPUT = "sin";
	String OUTPUT = "sout";

	@Input(INPUT)
	SubscribableChannel getStreamsInput();

	@Output(OUTPUT)
	MessageChannel streamsOutput();

}
