package com.example.calculatorservice;

import java.util.concurrent.atomic.AtomicLong;

import org.mariuszgromada.math.mxparser.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculationController {

	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/calculate")
	public CalculationResult
	calculate(@RequestParam(value = "expression", defaultValue = "") String expression) {
		expression = expression.replaceAll("^\"|\"$", "");
		Expression ex = new Expression(expression);
		String resultString = String.valueOf(ex.calculate());
		return new CalculationResult(counter.incrementAndGet(), resultString);
	}

}