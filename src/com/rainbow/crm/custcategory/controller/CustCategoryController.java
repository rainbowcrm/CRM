package com.rainbow.crm.custcategory.controller;



import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.custcategory.service.ICustCategoryService;
import com.rainbow.crm.database.GeneralSQLs;

public class CustCategoryController extends CRMCRUDController{
	
	public IBusinessService getService() {
		ICustCategoryService serv = (ICustCategoryService) SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
		return serv;
	}

	public Map <String, String > getEvaluationPeriods() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_EVALDATE);
		return ans;
	}
	public Map <String, String > getEvalCriteria() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_EVALCRIT);
		return ans;
	}
	
}
