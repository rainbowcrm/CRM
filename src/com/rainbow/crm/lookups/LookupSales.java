package com.rainbow.crm.lookups;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.utils.Utils;

public class LookupSales implements ILookupService{

	@Override
	public List<Object> lookupData(IRadsContext ctx,String searchString, int from, int noRecords, String lookupParam) {
		List<Object> ans = new ArrayList<Object>();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where billNumber like  '" + searchString + "'" ;
		}
		ISalesService service = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		List<? extends CRMModelObject> sls = service.listData(from, from  + noRecords, condition,(CRMContext)ctx,null);
		for (ModelObject obj :  sls) {
			ans.add(((Sales)obj).getBillNumber());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request.getSession().getId());
	}
}