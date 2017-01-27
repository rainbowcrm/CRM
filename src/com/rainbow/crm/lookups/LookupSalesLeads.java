package com.rainbow.crm.lookups;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.utils.Utils;

public class LookupSalesLeads implements ILookupService{

	@Override
	public List<Object> lookupData(IRadsContext ctx,String searchString, int from, int noRecords, String lookupParam) {
		List<Object> ans = new ArrayList<Object>();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where docNumber like  '" + searchString + "'" ;
		}
		ISalesLeadService service = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		List<? extends CRMModelObject> slsLeads = service.listData(from, from  + noRecords, condition,(CRMContext)ctx);
		for (ModelObject obj :  slsLeads) {
			ans.add(((SalesLead)obj).getDocNumber());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request.getSession().getId());
	}
}