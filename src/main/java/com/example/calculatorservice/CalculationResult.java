package com.example.calculatorservice;

public class CalculationResult {

	private final long id;
	private final String content;

	public CalculationResult(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
}