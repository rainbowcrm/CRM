package com.rainbow.crm.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.framework.setup.model.Metadata;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.techtrade.rads.framework.context.IRadsContext;

public class CommonUtil {
	
	private static Map <String,Metadata> metadataMap = new HashMap<String,Metadata> ();

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
	
	public static Metadata getMetaDataforClass(String classId)
	{
		if(metadataMap.containsKey(classId)) {
			return metadataMap.get(classId) ;
		}else {
			List<Metadata> metadataList = MetadataSQL.getMetadata();
			metadataList.forEach( metadata -> {   
				String className  =metadata.getClassName() ;
				StringTokenizer tokenizer = new StringTokenizer(className,".");
				int countTokens =  tokenizer.countTokens();
				String lastToken = null ;
				while(tokenizer.hasMoreElements()) {
					lastToken = String.valueOf(tokenizer.nextElement());
				}
				metadataMap.put(lastToken, metadata);
			});
			return metadataMap.get(classId) ;
		}
	}

}
