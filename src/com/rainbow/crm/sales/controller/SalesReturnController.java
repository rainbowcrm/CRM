package com.rainbow.crm.sales.controller;

import java.util.List;

import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public class SalesReturnController extends CRMTransactionController{

	@Override
	public ITransactionService getService() {
		ISalesService serv = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		return serv;
	}

	@Override
	public List<RadsError> adaptfromUI(ModelObject modelObject) {
		Sales sales =(Sales) modelObject;
		sales.setReturn(true);
		return super.adaptfromUI(sales);
	}

	@Override
	public List<RadsError> adapttoUI(ModelObject modelObject) {
		Sales sales =(Sales) modelObject;
		sales.setReturn(true);
		return super.adaptfromUI(sales);
	}
	
	

	
}
