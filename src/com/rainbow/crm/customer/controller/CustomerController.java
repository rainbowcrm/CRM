package com.rainbow.crm.customer.controller;



import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.service.ICustomerService;

public class CustomerController extends CRMCRUDController{
	
	public IBusinessService getService() {
		ICustomerService serv = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		return serv;
	}

}
