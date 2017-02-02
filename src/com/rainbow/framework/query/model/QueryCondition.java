package com.rainbow.framework.query.model;

import com.rainbow.crm.common.finitevalue.FiniteValue;

public class QueryCondition {

	int noOpenBrackets;
	int noCloseBrackets;

	FiniteValue dataType;

	String field;
	String operator;
	String value;

	String postCondition;

	public int getNoOpenBrackets() {
		return noOpenBrackets;
	}

	public void setNoOpenBrackets(int noOpenBrackets) {
		this.noOpenBrackets = noOpenBrackets;
	}

	public int getNoCloseBrackets() {
		return noCloseBrackets;
	}

	public void setNoCloseBrackets(int noCloseBrackets) {
		this.noCloseBrackets = noCloseBrackets;
	}

	public FiniteValue getDataType() {
		return dataType;
	}

	public void setDataType(FiniteValue dataType) {
		this.dataType = dataType;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPostCondition() {
		return postCondition;
	}

	public void setPostCondition(String postCondition) {
		this.postCondition = postCondition;
	}

}
