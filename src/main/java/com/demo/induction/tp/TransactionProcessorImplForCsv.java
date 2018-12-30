/**
 * 
 */
package com.demo.induction.tp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.induction.tp.util.CsvRowToObject;
import com.demo.induction.tp.util.TransactionDrCrChecker;
import com.demo.induction.tp.util.TransactionViolationChecker;

/**
 * @author suresh
 *
 */
@Service(value="txProcessorForCsvData")
public class TransactionProcessorImplForCsv implements TransactionProcessor{

	@Autowired
	private final TransactionViolationChecker violationChecker;
	@Autowired
	private final TransactionDrCrChecker drCrChecker;
	@Autowired
	private final CsvRowToObject csvRowToObject;
	private List<Transaction> transactions;
	
	public TransactionProcessorImplForCsv(TransactionViolationChecker violationChecker
			, TransactionDrCrChecker drCrChecker, CsvRowToObject csvRowToObject) {
		this.violationChecker = violationChecker;
		this.drCrChecker = drCrChecker;
		this.csvRowToObject = csvRowToObject;
	}

	@Override
	public void importTransactions(InputStream is) throws Exception{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			transactions = bufferedReader.lines().map(csvRowToObject.mapToItem).collect(Collectors.toList());
			bufferedReader.close();
	}

	@Override
	public List<Transaction> getImportedTransactions() {
		return transactions;
	}

	@Override
	public List<Violation> validate() {
		return violationChecker.findInputViolationsIn(transactions);
	}

	@Override
	public boolean isBalanced() {
		return drCrChecker.isDrCrBalancedIn(transactions);
	}
	
}
