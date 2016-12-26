package com.rainbow.crm.config.model;

import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ConfigLine  extends ModelObject{
	
	String configCode; 
	int company ;
	String description ;
	String group;
	FiniteValue valueType;
	String valueGenerator;
	String defaultValue; 
	String configValue ;
	String value; 
	
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	

	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public FiniteValue getValueType() {
		return valueType;
	}
	public void setValueType(FiniteValue valueType) {
		this.valueType = valueType;
	}
	public String getValueGenerator() {
		return valueGenerator;
	}
	public void setValueGenerator(String valueGenerator) {
		this.valueGenerator = valueGenerator;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
