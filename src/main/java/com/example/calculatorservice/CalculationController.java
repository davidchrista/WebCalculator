package com.example.calculatorservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculationController {

	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/calcget")
	public CalculationResult
	getCalcResultGET(@RequestParam(value = "expression", defaultValue = "") String expression) {
		expression = expression.replaceAll("^\"|\"$", "");
		return getCalcResult(expression);
	}
	
	@PostMapping("/calcpost")
	public CalculationResult
	getCalcResultPOST(@RequestBody Expression expression) {
		final String stringExpression = expression.getLeft() + expression.getOp() + expression.getRight();
		return getCalcResult(stringExpression);
	}
	
	private CalculationResult getCalcResult(String expression) {
		final org.mariuszgromada.math.mxparser.Expression ex = new org.mariuszgromada.math.mxparser.Expression(expression);
		final String resultString = String.valueOf(ex.calculate());
		return new CalculationResult(counter.incrementAndGet(), resultString);
	}

}