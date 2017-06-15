package com.rainbow.crm.salesperiod.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.product.model.Product;

public class SalesPeriodProduct extends CRMBusinessModelObject {

	String period;
	int lineNumber;
	String comments;
	double lineTotal;

	Product product;

	boolean voided;

	SalesPeriod salesPeriodDoc;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isVoided() {
		return voided;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	public SalesPeriod getSalesPeriodDoc() {
		return salesPeriodDoc;
	}

	public void setSalesPeriodDoc(SalesPeriod salesPeriodDoc) {
		this.salesPeriodDoc = salesPeriodDoc;
	}
	
	

	
}
