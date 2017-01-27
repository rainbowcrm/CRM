package com.rainbow.crm.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.techtrade.rads.framework.context.IRadsContext;

public class CommonUtil {
	

	public static User getUser(CRMContext context, String userId){
		IUserService service = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
		User user  = (User) service.getById(userId);
		return user;
	}
	
	public static boolean isManagerRole(User user) {
		if (CRMConstants.ROLETYPE.CORPADMIN.equals(user.getRoleType())  ||  CRMConstants.ROLETYPE.SYSADMIN.equals(user.getRoleType()) 
				|| CRMConstants.ROLETYPE.MANAGER.equals(user.getRoleType()) )
			return true;
		else
			return false;
	}
	
	public static boolean allowAllDivisionAccess(CRMContext ctx)  {
		User user = ctx.getLoggedInUser();
		String allow = isManagerRole(user) ? ConfigurationManager
				.getConfig(ConfigurationManager.MANAGER_ACC_ALLDIV, ctx)
				: ConfigurationManager.getConfig(ConfigurationManager.ASSOCIATE_ACC_ALLDIV, ctx);
		boolean allowAll = Boolean.parseBoolean(allow);
		return allowAll;
	}
	
	public static IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response) {
		
		CRMContext context=  LoginSQLs.loggedInUser(request.getSession().getId());
		User user = CommonUtil.getUser(context, context.getUser());
		context.setLoggedInUser(user);
		return context;
	}
	
	public static IRadsContext generateContext(String authToken) {
		CRMContext context=  LoginSQLs.loggedInUser(authToken);
		User user = CommonUtil.getUser(context, context.getUser());
		context.setLoggedInUser(user);
		return context;
	}

}
