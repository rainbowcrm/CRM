package com.rainbow.framework.setup.model;

import com.rainbow.crm.common.finitevalue.FiniteValue;

public class EntityField {

	String entity;
	String keyField;
	String hqlField;
	String description;
	FiniteValue dataType;
	String fvType;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getHqlField() {
		return hqlField;
	}
	public void setHqlField(String hqlField) {
		this.hqlField = hqlField;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FiniteValue getDataType() {
		return dataType;
	}
	public void setDataType(FiniteValue dataType) {
		this.dataType = dataType;
	}
	public String getFvType() {
		return fvType;
	}
	public void setFvType(String fvType) {
		this.fvType = fvType;
	}
	public EntityField(String entity, String keyField, String hqlField,
			String description, FiniteValue dataType, String fvType) {
		super();
		this.entity = entity;
		this.keyField = keyField;
		this.hqlField = hqlField;
		this.description = description;
		this.dataType = dataType;
		this.fvType = fvType;
	}
	
	
	
	
}
