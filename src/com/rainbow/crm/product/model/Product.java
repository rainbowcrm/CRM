package com.rainbow.crm.product.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class Product extends CRMBusinessModelObject{

	String name;
	Category category;
	String description;
	
	

	@RadsPropertySet(isBK=true)
	public String getName() {
		return name;
	}

	@RadsPropertySet(isBK=true)
	public void setName(String name) {
		this.name = name;
	}


	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Category getCategory() {
		return category;
	}

	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
}
