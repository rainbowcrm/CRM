package com.rainbow.crm.lookups;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.utils.Utils;

public class LookupCustomers implements ILookupService{
	
	@Override
	public List<Object> lookupData(IRadsContext ctx,String searchString, int from, int noRecords) {
		List<Object> ans = new ArrayList<Object>();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where firstName like  '" + searchString + "' or  lastName like  '" + searchString + "' " ;
		}
		ICustomerService service = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		List<? extends CRMModelObject> customers = service.listData(from, from  + noRecords, condition,(CRMContext)ctx);
		for (ModelObject obj :  customers) {
			ans.add(((Customer)obj).getFullName());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}

}
