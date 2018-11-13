package com.stream.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Karthik
 *
 */
@Component
public class EventListener {

	private Log LOGGER = LogFactory.getLog(EventListener.class);

	@StreamListener(OrderChannelConfig.INPUT)
	public void writeEvent(String event) {
		LOGGER.info("====== EVENT LISTENER :" + event);
	}

	@StreamListener(OrderChannelConfig.KAFKA_INPUT)
	public void streamProcess(KStream<String, String> stream) {
		LOGGER.info("================= KAFKA STREAMS LISTENER ===============");

		stream.foreach((key, value) -> System.out.println("Key :" + key + ", Value:" + value));
		KTable<String, Long> messageCountTable = stream.groupByKey().count(Materialized.as("message-count"));
		messageCountTable.toStream().foreach((key, value) -> System.out.println(key + ":" + value));

	}

}