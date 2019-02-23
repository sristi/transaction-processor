/**
 * 
 */
package com.demo.induction.tp.util;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.demo.induction.tp.Transaction;

/**
 * @author suresh
 * A wrapper class to hold the XML data while unmarshalling
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="TransactionList")
public class TransactionXmlWrapper implements Serializable{
	
	@XmlElement(name="Transaction")
	private List<Transaction> transactionList;

	public TransactionXmlWrapper() {}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

/*	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}*/
}
