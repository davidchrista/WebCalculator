package com.example.calculatorservice;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculationControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void exampleCalculationGET() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/calcget?expression=\"1+1\"").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"content\":\"2.0\"")));
	}

	@Test
	public void exampleCalculationPOST() throws Exception {
		Expression ex = new Expression("1", "1", "+");
		mvc.perform(MockMvcRequestBuilders.post("/calcpost").header("content-type", "application/json").accept(MediaType.APPLICATION_JSON)
					.content("{\"right\":\"" + ex.getRight() + "\",\"left\":\"" + ex.getLeft() + "\",\"op\":\"" + ex.getOp() + "\"}"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"content\":\"2.0\"")));
	}

}