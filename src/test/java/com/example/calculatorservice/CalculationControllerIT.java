package com.example.calculatorservice;

import static org.assertj.core.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculationControllerIT {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http", "localhost", port , "/");
	}

	@Test
	public void exampleCalculationGET() throws Exception {
		URL url = new URL(base.toString() + "calcget");
		ResponseEntity<String> response = template.getForEntity(url.toString() + "?expression=\"5*3\"", String.class);
		assertThat(response.getBody()).contains("\"content\":\"15.0\"");
	}

	@Test
	public void exampleCalculationPOST() throws Exception {
		URL url = new URL(base.toString() + "calcpost");
		Expression ex = new Expression("1", "1", "+");
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Expression> request = new HttpEntity<>(ex, headers);
		ResponseEntity<String> response = template.postForEntity(url.toString(), request, String.class);
		assertThat(response.getBody()).contains("\"content\":\"2.0\"");
	}

}