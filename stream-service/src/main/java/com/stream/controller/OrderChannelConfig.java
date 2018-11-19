package com.stream.controller;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import com.stream.dto.Product;

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
	String PRODUCT_INPUT = "products";
	String PRODUCT_SALES_INPUT = "products-sales";
	String PRODUCT_OUTPUT = "products-out";

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

	@Input(PRODUCT_INPUT)
	KStream<String, Product> productStreamInput();

	@Output(PRODUCT_OUTPUT) // producer
	MessageChannel productStreamOutput();

	//@Input(PRODUCT_SALES_INPUT)
	//KStream<String, ProductSales> productSalesStreamInput();
}
