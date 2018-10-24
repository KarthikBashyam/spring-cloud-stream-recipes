package com.stream.controller;

public class OrderCreatedEvent {

	private String name;

	public OrderCreatedEvent(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
