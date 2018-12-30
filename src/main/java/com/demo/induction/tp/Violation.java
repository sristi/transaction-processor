package com.demo.induction.tp;

public class Violation {
    private int order;
    private String property;
    private String description;
    
	public Violation(int order, String property, String description) {
		this.order = order;
		this.property = property;
		this.description = description;
	}

	public int getOrder() {
		return order;
	}

	public String getProperty() {
		return property;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Violation [order=" + order + ", property=" + property + ", description=" + description + "]";
	}    
}
