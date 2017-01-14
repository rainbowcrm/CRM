package com.rainbow.crm.lookups;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.utils.Utils;

public class LookupBrands implements ILookupService{

	@Override
	public List<Object> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam) {
		List<Object> ans = new ArrayList<Object>();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where name like  '" + searchString + "'" ;
		}
		IBrandService service = (IBrandService) SpringObjectFactory.INSTANCE.getInstance("IBrandService");
		List<? extends CRMModelObject> items = service.listData(from, from  + noRecords, condition,(CRMContext)ctx);
		for (ModelObject obj :  items) {
			ans.add(((Brand)obj).getName());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}
	
	

}
