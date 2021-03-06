package com.stream.serdes;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.dto.Product;

public class ProductSerializer implements Serializer<Product> {

	private static final Log LOGGER = LogFactory.getLog(ProductSerializer.class);

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] serialize(String topic, Product data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			LOGGER.info("Failed to serialize object : " + e.getMessage());
		}
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
