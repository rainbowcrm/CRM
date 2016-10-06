package com.rainbow.crm.salesperiod.model;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesPeriodLine extends CRMItemLine{
	String period;
	int lineNumber;
	double targetPrice;
	String comments;
	double lineTotal;
	
	SalesPeriod salesPeriodDoc;
	
	public SalesPeriodLine() {
		id =2;
	}
	
	@RadsPropertySet(isBK=true)
	public String getPeriod() {
		return period;
	}
	@RadsPropertySet(isBK=true)
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public SalesPeriod getSalesPeriodDoc() {
		return salesPeriodDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setSalesPeriodDoc(SalesPeriod salesPeriodDoc) {
		this.salesPeriodDoc = salesPeriodDoc;
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	

}
