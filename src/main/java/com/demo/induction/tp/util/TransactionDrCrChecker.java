/**
 * 
 */
package com.demo.induction.tp.util;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.demo.induction.tp.Transaction;

/**
 * @author suresh
 * A utility class where checking of Dr and Cr balances of Transactions get checked.
 * 
 */
@Component(value="drCrChecker")
public class TransactionDrCrChecker {
	
	/**
	 * @param transactions
	 * @return true only if the @param transactions is non-empty and sum of 
	 * Dr and sum of Cr balances are equal; false otherwise.
	 */
	public boolean isDrCrBalancedIn(List<Transaction> transactions)
	{
		BigDecimal drSum = BigDecimal.ZERO;
		BigDecimal crSum = BigDecimal.ZERO;

		for(Transaction transaction:transactions)
		{
			BigDecimal transactionAmount = transaction.getAmount();
			String txType = transaction.getType();
			if(txType.equalsIgnoreCase("D"))
				drSum = drSum.add(transactionAmount);
			else if(txType.equalsIgnoreCase("C"))
				crSum = crSum.add(transactionAmount);
		}

		if(null!=transactions && transactions.size()>0 && drSum.compareTo(crSum)==0)
			return true;
		else
			return false;
	}
}
