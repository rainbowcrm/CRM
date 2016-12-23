package com.rainbow.crm.custcategory.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="CustCategory",xmlTag="CustCategory")
public class CustCategory extends CRMBusinessModelObject{
   
	String name ;
	FiniteValue evalFrom;
	FiniteValue evalTo ;
	FiniteValue evalCriteria;
	double minValue;
	double maxValue;
	boolean incudeReturns;
	String excludedCustomers;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FiniteValue getEvalFrom() {
		return evalFrom;
	}
	public void setEvalFrom(FiniteValue evalFrom) {
		this.evalFrom = evalFrom;
	}
	public FiniteValue getEvalTo() {
		return evalTo;
	}
	public void setEvalTo(FiniteValue evalTo) {
		this.evalTo = evalTo;
	}
	public FiniteValue getEvalCriteria() {
		return evalCriteria;
	}
	public void setEvalCriteria(FiniteValue evalCriteria) {
		this.evalCriteria = evalCriteria;
	}
	public double getMinValue() {
		return minValue;
	}
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	public boolean isIncudeReturns() {
		return incudeReturns;
	}
	public void setIncudeReturns(boolean incudeReturns) {
		this.incudeReturns = incudeReturns;
	}
	public String getExcludedCustomers() {
		return excludedCustomers;
	}
	public void setExcludedCustomers(String excludedCustomers) {
		this.excludedCustomers = excludedCustomers;
	}
	
	

}
