package com.rainbow.crm.sales.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.item.model.Item;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesLine extends CRMItemLine{
	String billNumber;
	int lineNumber;
	
	double unitPrice;
	
	String comments;
	double unitDisc;
	double discPercent;
	double lineTotalDisc;
	double taxPercent;
	double taxAmount;
	double lineTotal;
	
	Sales salesDoc;
	
	public SalesLine() {
	}
	
		
	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}



	@RadsPropertySet(isBK=true)
	public int getLineNumber() {
		return lineNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public double getUnitDisc() {
		return unitDisc;
	}
	public void setUnitDisc(double unitDisc) {
		this.unitDisc = unitDisc;
	}
	public double getDiscPercent() {
		return discPercent;
	}
	public void setDiscPercent(double discPercent) {
		this.discPercent = discPercent;
	}
	public double getLineTotalDisc() {
		return lineTotalDisc;
	}
	public void setLineTotalDisc(double lineTotalDisc) {
		this.lineTotalDisc = lineTotalDisc;
	}
	public double getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(double taxPercent) {
		this.taxPercent = taxPercent;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public Sales getSalesDoc() {
		return salesDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setSalesDoc(Sales salesDoc) {
		this.salesDoc = salesDoc;
	}
	
	

}
