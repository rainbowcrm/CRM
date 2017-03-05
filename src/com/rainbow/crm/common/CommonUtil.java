package com.rainbow.crm.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;












import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.framework.setup.model.Metadata;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.utils.Utils;

public class CommonUtil {
	
	private static Map <String,Metadata> metadataMap = new HashMap<String,Metadata> ();

	private static String [] colors = { "Red","Blue" ,"Green" , "Violet" , "Indigo" , "Majenta" ,"Brown" ,"Yellow" , "Orange", 
			"Salmon","Gray","SandyBrown","Ivory","CadetBlue","OrangeRed","SeaGreen"} ;
	
	public static String [] getGraphColors() {
		return colors;
	}
	
	public static Date getRelativeDate(FiniteValue dateValue) {
			if(CRMConstants.RELDATE.TODAY.equals(dateValue.getCode())) {
				return new java.util.Date();
			}else if(CRMConstants.RELDATE.LASTWEEK.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (7l * 24l  * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTWEEK.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (7l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (30l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTWOMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (60l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTHREEMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (91l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTSIXMONTH.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (182l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTYEAR.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (365l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTWOYEAR.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (365l * 2l * 24l   * 3600l * 1000l));
			}else if(CRMConstants.RELDATE.LASTTHREEYEAR.equals(dateValue.getCode())) {
				return new java.util.Date (new java.util.Date().getTime() -  (365l * 3l * 24l   * 3600l * 1000l));
			}else
				return null;
	}
	
	
	public static User getUser(CRMContext context, String userId){
		IUserService service = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
		User user  = (User) service.getById(userId);
		return user;
	}
	
	public static Company getCompany(int company){
		ICompanyService service = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company comp  = (Company) service.getById(company);
		return comp;
	}
	
	public static boolean isManagerRole(User user) {
		if (CRMConstants.ROLETYPE.CORPADMIN.equals(user.getRoleType())  ||  CRMConstants.ROLETYPE.SYSADMIN.equals(user.getRoleType()) 
				|| CRMConstants.ROLETYPE.MANAGER.equals(user.getRoleType()) )
			return true;
		else
			return false;
	}
	
	private static boolean isCorporateUser(User user) {
		if (CRMConstants.ROLETYPE.CORPADMIN.equals(user.getRoleType())  ||  CRMConstants.ROLETYPE.SYSADMIN.equals(user.getRoleType()))
				return true;
		else
			return false;
	}
	
	public static boolean allowAllDivisionAccess(CRMContext ctx)  {
		User user = ctx.getLoggedInUser();
		if (isCorporateUser(user)) return true;
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
	
	public static Division getDivisionObect(CRMContext context , Division division) {
		IDivisionService divisionService = (IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		Division retdivision = null ;
		if(division.getId() > 0 ) {
			retdivision =(Division) divisionService.getById(division.getId());
		} else if (!Utils.isNullString(division.getName())) {
			retdivision =(Division) divisionService.getByBusinessKey(division, context);
		}
		return retdivision;
	}
	
	public static CRMModelObject getCRMModelObject(CRMContext context,CRMBusinessModelObject modelObject, String interfaceName ) {
		IBusinessService  businessService =(IBusinessService)  SpringObjectFactory.INSTANCE.getInstance(interfaceName);
		CRMBusinessModelObject retObject = null;
		if (modelObject == null)
			return null;
		if (  modelObject.getPK() != null ) {
			retObject = (CRMBusinessModelObject) businessService.getById(modelObject.getPK());
		}else if  (!Utils.isNullMap(modelObject.getBK())) {
			retObject = (CRMBusinessModelObject) businessService.getByBusinessKey(modelObject, context);
		}
		return retObject;
	}

}
