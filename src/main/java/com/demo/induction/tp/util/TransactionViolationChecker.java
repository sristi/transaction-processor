/**
 * 
 */
package com.demo.induction.tp.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.demo.induction.tp.Transaction;
import com.demo.induction.tp.Violation;

/**
 * @author suresh
 * A utility class to check if any Transaction(s) has input violations
 */
@Component(value="violationChecker")
public class TransactionViolationChecker {

	/**
	 * @param transactions
	 * @return collection of input violations as described in requirement.
	 */
	public List<Violation> findInputViolationsIn(List<Transaction> transactions)
	{
		List<Violation> violations = new ArrayList<>(); 
		for(Transaction transaction: transactions)
		{
			String type = transaction.getType();
			BigDecimal amount = transaction.getAmount();
			String narration = transaction.getNarration();
			
			if(null==type || type.trim().equals("") || !type.matches("D|C"))
				violations.add(new Violation(1, "type", "Type of transaction can not be null or empty or other than 'D' or 'C'. But supplied type is:"+type));
			if(null==amount || amount.compareTo(BigDecimal.ZERO)<=0)
				violations.add(new Violation(2, "amount", "Amount of transaction can not be null or zero or negative. But supplied value is "+amount));
			if(null==narration || narration.replaceAll("\\s+", "").isEmpty())
				violations.add(new Violation(3, "narration", "Narration of transaction can not be null or empty. But supplied narration is:"+narration));
		}
		return violations;		
	}
}
