package com.rainbow.crm.product.controller;

import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.product.service.IProductFAQService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ProductFAQController extends CRMTransactionController {

	@Override
	public PageResult submit(ModelObject object) {
		return null;
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		return super.submit(object, actionParam);
	}

	@Override
	public ITransactionService getService() {
		IProductFAQService faqService = (IProductFAQService)SpringObjectFactory.INSTANCE.getInstance("IProductFAQService");
		return faqService;
	}
	
	
	
	
	
	

}
