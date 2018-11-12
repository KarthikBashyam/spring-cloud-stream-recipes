package com.stream.controller;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
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
	String KAFKA_INPUT = "kin";
	String KAFKA_OUTPUT = "kout";

	// consumer
	@Input(INPUT)
	SubscribableChannel getStreamsInput();

	// producer
	@Output(OUTPUT)
	MessageChannel streamsOutput();

	@Input(KAFKA_INPUT)
	KStream<String, String> kafkaStreamsInput();

	@Input(KAFKA_OUTPUT)
	KTable<String, String> kafkaStreamsOutput();
}
