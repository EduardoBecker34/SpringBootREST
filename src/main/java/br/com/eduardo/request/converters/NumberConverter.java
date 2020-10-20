package br.com.eduardo.request.converters;

import org.springframework.stereotype.Service;

@Service
public class NumberConverter {

	public Double convertToDouble(String strNumber) {
		if (strNumber == null)
			return 0D;

		String number = strNumber.replaceAll(",", ".");
		if (isNumeric(number)) {
			return Double.parseDouble(strNumber);
		}

		return 0D;
	}

	public boolean isNumeric(String strNumber) {
		if (strNumber == null)
			return false;

		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
}
