package com.rainbow.crm.salesperiod.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.brand.model.Brand;

public class SalesPeriodBrand extends CRMBusinessModelObject {

	String period;
	int lineNumber;
	String comments;
	double lineTotal;

	Brand brand;

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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
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
