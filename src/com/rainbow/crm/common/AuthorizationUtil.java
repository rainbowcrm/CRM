package com.rainbow.crm.common;

import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.config.sql.ConfigSQL;
import com.rainbow.crm.user.model.User;

public class AuthorizationUtil {
	
	public static boolean isAccessAllowed(String accessCode, User  user)
	{
		//CORPADMIN, MANAGER,SALESASS,OFFASSOC,FLOORWRK
		//ALLUSR , MGRUSR ,ADMUSR 
		//ConfigSQL.getConfigforCode(context.getLoggedinCompany(), key);
		int companyId = user.getCompany().getId();
		
		user.getRoleType() ;
		if(accessCode == null )
			return true;
		String permission =null ;
		if(accessCode.contains("ADMIN:")){
			permission =  ConfigurationManager.CONFIGURATION_PRIVILEGE ;
		}
		if(accessCode.contains("ANALY:")){
			permission =  ConfigurationManager.ANALYTICAL_REPORTS_PRIVILEGE;
			
		}
		if(accessCode.contains("SPLAN:")){
			permission =  ConfigurationManager.TARGET_SETTING_PRIVILEGE;
			
		}
			String accessRight = ConfigSQL.getConfigforCode(companyId,permission);
			if (CRMConstants.USERTYPE.ALLUSERS.equals(accessRight))
				return true;
			else if (CRMConstants.USERTYPE.MANAGERUSERS.equals(accessRight) &&  CommonUtil.isManagerRole(user))
				return true;
			else if (CRMConstants.USERTYPE.ADMINUSERS.equals(accessRight) &&  CommonUtil.isCorporateUser(user))
				return true ;
		
		return true;	
	}

}
