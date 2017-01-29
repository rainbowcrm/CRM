package com.rainbow.crm.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;

public abstract class CRMGeneralController  extends GeneralController{
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response) {
		
		CRMContext context=  LoginSQLs.loggedInUser(request.getSession().getId());
		User user = CommonUtil.getUser(context, context.getUser());
		context.setLoggedInUser(user);
		return context;
	}
	
	@Override
	public IRadsContext generateContext(String authToken) {
		CRMContext context=  LoginSQLs.loggedInUser(authToken);
		User user = CommonUtil.getUser(context, context.getUser());
		context.setLoggedInUser(user);
		return context;
	}

}
