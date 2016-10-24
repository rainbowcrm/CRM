package com.rainbow.crm.lookups;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.utils.Utils;

public class LookupUsers implements ILookupService{

	@Override
	public List<Object> lookupData(IRadsContext ctx,String searchString, int from, int noRecords) {
		List<Object> ans = new ArrayList<Object>();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where userId like  '" + searchString + "'" ;
		}
		IUserService service = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
		List<? extends CRMModelObject> users = service.listData(from, from  + noRecords, condition,(CRMContext)ctx);
		for (ModelObject obj :  users) {
			ans.add(((User)obj).getUserId());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}

}
