package com.stream.serdes;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.dto.ProductSales;

public class ProductSalesDeserializer implements Deserializer<ProductSales> {

	private static final Log LOGGER = LogFactory.getLog(ProductSalesDeserializer.class);

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public ProductSales deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<ProductSales> responseEntity;
		try {
			responseEntity = mapper.readValue(data, new TypeReference<ProductSales>() {
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
