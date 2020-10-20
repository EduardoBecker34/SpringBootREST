package br.com.eduardo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.exception.UnsuportedMathOperationException;
import br.com.eduardo.math.SimpleMath;
import br.com.eduardo.request.converters.NumberConverter;

@RestController
public class MathController {

	@Autowired
	private SimpleMath math;
	@Autowired
	private NumberConverter numberConverter;

	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception {
		if (!numberConverter.isNumeric(numberOne) || !numberConverter.isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Por favor, defina um valor numérico");
		}

		return math.sum(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtract(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception {
		if (!numberConverter.isNumeric(numberOne) || !numberConverter.isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Por favor, defina um valor numérico");
		}

		return math.subtraction(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiplication(@PathVariable("numberOne") String numberOne,
			@PathVariable("numberTwo") String numberTwo) throws Exception {
		if (!numberConverter.isNumeric(numberOne) || !numberConverter.isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Por favor, defina um valor numérico");
		}

		return math.multiplication(numberConverter.convertToDouble(numberOne),
				numberConverter.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double division(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception {
		if (!numberConverter.isNumeric(numberOne) || !numberConverter.isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Por favor, defina um valor numérico.");
		}

		return math.division(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double mean(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception {
		if (!numberConverter.isNumeric(numberOne) || !numberConverter.isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Por favor, defina um valor numérico");
		}

		return math.mean(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "squareRoot/{numberOne}", method = RequestMethod.GET)
	public Double squareRoot(@PathVariable("numberOne") String numberOne) throws Exception {
		if (!numberConverter.isNumeric(numberOne)) {
			throw new UnsuportedMathOperationException("Por favor, defina um valor numérico");
		}

		return math.sqrt(numberConverter.convertToDouble(numberOne));
	}
}
