package com.demo.induction.tp;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Transaction")
public class Transaction implements Serializable{
	@XmlAttribute
    private String type;
	
	@XmlAttribute
    private BigDecimal amount;
	
	@XmlAttribute
    private String narration;
    
    public Transaction() {}//needed for xml unmarshalling
    public Transaction(String type, BigDecimal amount, String narration)
    {
    	this.type = type;
    	this.amount = amount;
    	this.narration = narration;
    }

	public String getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getNarration() {
		return narration;
	}

	@Override
	public String toString() {
		return "Transaction [type=" + type + ", amount=" + amount + ", narration=" + narration + "]";
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	
}
