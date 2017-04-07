package com.rainbow.crm.custcategory.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.framework.query.model.QueryCondition;

public class CustCategoryCondition extends QueryCondition{
	
	CustCategory category ;

	public CustCategory getCategory() {
		return category;
	}

	public void setCategory(CustCategory category) {
		this.category = category;
	}
	
	

}
