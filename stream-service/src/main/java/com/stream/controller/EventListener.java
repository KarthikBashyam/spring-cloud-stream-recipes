package com.stream.controller;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.stream.dto.Product;
import com.stream.dto.ProductSales;
import com.stream.serdes.ProductDeserializer;
import com.stream.serdes.ProductSalesDeserializer;
import com.stream.serdes.ProductSalesSerializer;
import com.stream.serdes.ProductSerializer;

/**
 * 
 * @author Karthik
 *
 */
@Component
public class EventListener {

	private static final Log LOGGER = LogFactory.getLog(EventListener.class);
	
	Deserializer<Product> productDes = new ProductDeserializer(); 
	Serializer<Product> productSer = new ProductSerializer(); 
	Deserializer<ProductSales> productSalesDes = new ProductSalesDeserializer(); 
	Serializer<ProductSales> productSalesSer = new ProductSalesSerializer();

	Serde<Product> productSerde = Serdes.serdeFrom(productSer,productDes);
	Serde<ProductSales> productSalesSerde = Serdes.serdeFrom(productSalesSer, productSalesDes);
	
	@StreamListener(OrderChannelConfig.INPUT)
	public void writeEvent(String event) {
		LOGGER.info("====== EVENT LISTENER :" + event);
	}

	@StreamListener(OrderChannelConfig.KAFKA_INPUT)
	public void streamProcess(KStream<String, String> stream) {
		LOGGER.info("================= KAFKA STREAMS LISTENER ===============");
		// Creates a Queryable local store.
		stream.foreach((key, value) -> System.out.println("Key :" + key + ", Value:" + value));
		KTable<String, Long> messageCountTable = stream.groupByKey().count(Materialized.as("message-count"));
		messageCountTable.toStream().foreach((key, value) -> System.out.println(key + ":" + value));

	}

	@StreamListener
	public void productStreamProcess(
			@Input(OrderChannelConfig.PRODUCT_SALES_INPUT) KStream<String, ProductSales> productSalesStream,
			@Input(OrderChannelConfig.PRODUCT_INPUT) KStream<String, Product> productStream) {

		LOGGER.info("================= PRODUCT STREAMS LISTENER ===============");
		productSalesStream.foreach((key, value) -> System.out.println(value));
		productStream.foreach((key, value) -> System.out.println(value));
		KStream<String, String> productSalesTable =  productStream.outerJoin(productSalesStream,
							(products, sales) -> {return sales.getSalesCount().toString();},
							JoinWindows.of(TimeUnit.SECONDS.toSeconds(20)),
							Joined.with(Serdes.String(), productSerde, productSalesSerde)
							);
							
	
		productSalesTable.foreach((key, value) -> System.out.println(key + " : " + value));

	}

}