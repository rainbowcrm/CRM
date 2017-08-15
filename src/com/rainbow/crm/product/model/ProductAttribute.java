package com.rainbow.crm.product.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class ProductAttribute extends  CRMBusinessModelObject
{
	Product product ;
	String attribute;
	FiniteValue valueType;
	String comments;
	
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public Product getProduct() {
		return product;
	}
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public FiniteValue getValueType() {
		return valueType;
	}
	public void setValueType(FiniteValue valueType) {
		this.valueType = valueType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	


}
