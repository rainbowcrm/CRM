package com.rainbow.crm.saleslead.controller;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.saleslead.validator.SalesLeadValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalesLeadListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		ISalesLeadService serv = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		SalesLead salesLead = (SalesLead) object;
		return salesLead.getId();
				
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		PageResult result = new PageResult();
		ISalesLeadService service = (ISalesLeadService) getService();
		for (ModelObject lead : objects) {
			service.startSalesCycle((SalesLead)lead);
		}
		return result;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		SalesLeadValidator validator = new SalesLeadValidator((CRMContext) getContext());
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newsaleslead");
		return result;
	}
	
	

}
