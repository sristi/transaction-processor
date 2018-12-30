/**
 * 
 */
package com.demo.induction.tp.util;

import java.math.BigDecimal;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.demo.induction.tp.Transaction;

/**
 * @author suresh
 *
 */
@Component(value="csvRowToObject")
public class CsvRowToObject {
	
	public Function<String, Transaction> mapToItem = (line) -> {
		String[] input = line.split(",");
		return new Transaction(input[0],BigDecimal.valueOf(Double.valueOf(input[1]) ),input[2]);
	};
}
