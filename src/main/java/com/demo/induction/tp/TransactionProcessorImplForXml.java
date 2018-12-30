/**
 * 
 */
package com.demo.induction.tp;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.induction.tp.util.TransactionDrCrChecker;
import com.demo.induction.tp.util.TransactionViolationChecker;
import com.demo.induction.tp.util.TransactionXmlWrapper;

/**
 * @author suresh
 *
 */
@Service(value="txProcessorForXmlData")
public class TransactionProcessorImplForXml implements TransactionProcessor{

	@Autowired
	private final TransactionViolationChecker violationChecker;
	@Autowired
	private final TransactionDrCrChecker drCrChecker;
	private List<Transaction> transactions;
	
	public TransactionProcessorImplForXml(TransactionViolationChecker violationChecker, TransactionDrCrChecker drCrChecker) {
		this.violationChecker = violationChecker;
		this.drCrChecker = drCrChecker;
	}

	@Override
	public void importTransactions(InputStream is) throws Exception{
			JAXBContext jaxbContext = JAXBContext.newInstance(TransactionXmlWrapper.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        TransactionXmlWrapper transactionList = (TransactionXmlWrapper)unmarshaller.unmarshal(is);
	        transactions = transactionList.getTransactionList();
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
