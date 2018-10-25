package com.stream.controller;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
	
	@StreamListener(OrderChannelConfig.INPUT)
	public void writeEvent(String event) {
		System.out.println("====== EVENT LISTENER :" + event);
	}

}
