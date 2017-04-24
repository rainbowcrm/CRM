package com.rainbow.crm.loyalty.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.loyalty.model.Loyalty;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface ILoyaltyService extends IBusinessService{

	  
	public void addToLoyalty(int salesId,CRMContext context) ;
	

}
