package com.rainbow.crm.transactiondocuments.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.rainbow.crm.distributionorder.service.IDistributionOrderService;
import com.rainbow.crm.transactiondocuments.model.TransactionDocument;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class TransactionDocumentController extends GeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		TransactionDocument transDoc= (TransactionDocument)object ;
		if("SalesInvoice".equalsIgnoreCase(transDoc.getDocType())) {
			
		}else if("ShippingLabel".equalsIgnoreCase(transDoc.getDocType())) {
			IDistributionOrderService service = (IDistributionOrderService)SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService") ;
			DistributionOrder order = (DistributionOrder)service.getById(transDoc.getPK());
			String doc = service.generateShippingLabel(order,(CRMContext) getContext());
			transDoc.setDocument(doc);
		}
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRadsContext generateContext(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
