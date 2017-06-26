package com.rainbow.crm.config.service;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.config.sql.ConfigSQL;

public class ConfigurationManager {

	public static final String CURRENCY = "CURRENCY";
	public static final String CONFIGURATION_PRIVILEGE = "CONFPRIVIL";
	public static final String ANALYTICAL_REPORTS_PRIVILEGE = "ANLREPPRIVIL";
	public static final String TARGET_SETTING_PRIVILEGE = "TRGSETPRIV";
	public static final String REPORT_VIEW_PRIVILEGE = "REPVIEWPRIV";
	
	public static final String ALLOW_RETURNS = "ALLOWRETURNS";
	public static final String IMAGE_SERVER_HOST = "IMGSRVHST";
	public static final String IMAGE_SERVER_USER = "IMGSRVUSR";
	public static final String IMAGE_SERVER_PASSWORD = "IMGSRVPWD";
	public static final String IMAGE_SERVER_PORT = "IMGSRVPRT";
	public static final String IMAGE_SERVER_URL = "IMGSRVURL";
	
	public static final String SLS_AMOUNT_UNIT_LOYALTY = "LOYALTY";
	public static final String REDEEM_LOYALTY = "RDMLOYALTY";
	public static final String REDUCE_LOYALTY_RETURNS = "RDCLYLTRT";
	public static final String LEAD_EMAIL_TEMPLATE = "LDEMAIL";
	
	public static final String TRACK_INVENTORY = "TRCKINVEN";
	public static final String ALLOW_INVENTORY_TRANSFERS = "ALLWTRAN";
	public static final String ALLOW_SALE_WITHNO_INVENTORY = "ALLWSALE";
	public static final String SALES_INVOICE_TEMPLATE = "SLSINVPRN";
	
	
	public static final String CAPTURE_TENDERDETAILS = "CAPTTEND";
	
	public static final String ALLOW_SHIPPING_FROMMULTI_DIV = "MULIDIVSHIP";
	public static final String ORDER_LABEL_TEMPLATE = "ORDLBLPRIN";
	
	public static final String SALESPERSONAL_ASSOCIATION = "ASSSLSPERHDLIN";
	public static final String ASSOCIATE_ACC_ALLDIV = "ALLDIVACCASS";
	public static final String MANAGER_ACC_ALLDIV = "ALLDIVACCGMR";
	
	public static String  getConfig(String key , CRMContext context) {
		return ConfigSQL.getConfigforCode(context.getLoggedinCompany(), key);
	}
}
