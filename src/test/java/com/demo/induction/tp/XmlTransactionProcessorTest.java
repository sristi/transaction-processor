package com.demo.induction.tp;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import com.demo.induction.tp.util.TransactionDrCrChecker;
import com.demo.induction.tp.util.TransactionViolationChecker;

public class XmlTransactionProcessorTest {
	
	static TransactionProcessor transactionProcessor;

	@BeforeClass
	public static void setUp() throws Exception
	{
		transactionProcessor = new TransactionProcessorImplForXml(new TransactionViolationChecker(), new TransactionDrCrChecker());
		File file = new File("src/test/resources/transaction_import_data.xml");
	    InputStream inputStream;
		inputStream = new FileInputStream(file);
		transactionProcessor.importTransactions(inputStream);
	}
	
	/**
	 *  Test to assert the List of Transactions gets initialized from stream
	 */
	@Test
	public void testImportedTransactionListIsOk()
	{
		assertTrue(transactionProcessor.getImportedTransactions().size()>0);
	}
	
	/**
	 * Test to assert there is no input violations in the transaction list imported
	 */
	@Test
	public void testImportedTransactionListHasNoInputViolation()
	{
		assertTrue(transactionProcessor.validate().size()==0);
	}
	
	/**
	 * Test to assert transaction list imported has balanced amount of Dr and Cr
	 */
	@Test
	public void testImportedTransactionListHasBalancedDrAndCr()
	{
		assertTrue(transactionProcessor.isBalanced());
	}
}
