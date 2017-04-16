package com.rainbow.crm.saleslead.controller;

import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalesLeadExtendedController extends CRMTransactionController{

	@Override
	public PageResult submit(ModelObject object) {
		return null;
	}
	
	public ISalesLeadService getService() {
		ISalesLeadService serv = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		return serv;
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		
		return super.submit(object, actionParam);
	}
	
	
	
	
	

}
