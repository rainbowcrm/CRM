package com.rainbow.crm.sales.controller;

import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesReturnSearch;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.sales.validator.SalesErrorCodes;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesReturnSearchController  extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		SalesReturnSearch search = (SalesReturnSearch) object; 
		if(search != null && !Utils.isNull(search.getOriginalBilllNumber())) {
			ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService") ;
			Sales sales = salesService.getByBillNumberforReturn(search.getDivision(), search.getOriginalBilllNumber());
			PageResult result= new PageResult();
			if(sales == null)
			{
				result.addError(CRMValidator.getErrorforCode(getContext().getLocale() ,SalesErrorCodes.SALESBILL_NOT_FOUND, search.getOriginalBilllNumber()));
				return result;
			}else
			{
				
			}
		}
			
		return new PageResult();
	}

	
}
