package com.rainbow.framework.query.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;

public class Query extends CRMBusinessModelObject{

	String entity;
	Division division;
	Date fromDate;
	Date toDate;
	
	FiniteValue fromCriteria;
	FiniteValue toCriteria;
	
	String dateValueType;
	

	List<QueryCondition> conditions;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<QueryCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}

	public void addCondition(QueryCondition condition) {
		if (conditions == null)
			conditions = new ArrayList<QueryCondition>();
		this.conditions.add(condition);
	}

	public FiniteValue getFromCriteria() {
		return fromCriteria;
	}

	public void setFromCriteria(FiniteValue fromCriteria) {
		this.fromCriteria = fromCriteria;
	}

	public FiniteValue getToCriteria() {
		return toCriteria;
	}

	public void setToCriteria(FiniteValue toCriteria) {
		this.toCriteria = toCriteria;
	}

	public String getDateValueType() {
		return dateValueType;
	}

	public void setDateValueType(String dateValueType) {
		this.dateValueType = dateValueType;
	}

	
	
}
