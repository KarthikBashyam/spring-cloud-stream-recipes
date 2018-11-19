package com.stream.serdes;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.dto.Product;

public class ProductDeserializer implements Deserializer<Product> {

	private static final Log LOGGER = LogFactory.getLog(ProductDeserializer.class);

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public Product deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<Product> responseEntity;
		try {
			responseEntity = mapper.readValue(data, new TypeReference<Product>() {
			});
			return responseEntity.getBody();
		} catch (IOException e) {
			LOGGER.info("Failed to deserialize object : " + e.getMessage());
		}
		return null;
	}

	@Override
	public void close() {

	}

}
