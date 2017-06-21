package com.rainbow.crm.common;

public class CRMConstants {

	public final static String FV_INDUSTRY_TYPE = "INDUSTRY";
	public final static String FV_DIVISION_TYPE = "DIVTYPE";
	public final static String FV_BUSINESS_TYPE = "BUSINESS";
	public final static String FV_ITEMCLASS_TYPE = "ITEMCLASS";
	public final static String FV_ROLE_TYPE = "ROLE";
	public final static String FV_UOM_TYPE = "UOMTYPE";
	public final static String FV_WISHLIST_REASONTYPE = "WISHREASON";
	public final static String FV_SALESCYCLE_STATUS = "SLCYCSTS";
	public final static String FV_CONTACT_TYPE = "CONTTYPE";
	public final static String FV_COMMUNICATION_MODE = "COMMMODE";
	public final static String FV_DELIVERY_MODE = "DLVMODE";
	public final static String FV_ORDERTYPE = "SLORDTYPE";
	public final static String FV_CONFIDENCE_LEVEL = "CONFLEVEL";
	public final static String FV_FOLLOWUP_RESULT = "FLPRESULT";
	public final static String FV_SUCCESS_REASON = "SUCCREAS";
	public final static String FV_FAILURE_REASON = "FAILREAS";
	public final static String FV_ALERT_TYPE = "ALTTYPE";
	public final static String FV_ALERT_STATUS = "ALTSTATUS";
	public final static String FV_EVALDATE = "EVALDATE";
	public final static String FV_EVALCRIT = "EVALCRT";
	public final static String FV_CONFVALTYPE = "CONFVALTYP";
	public final static String FV_SPFTYPE = "SPFTYPE";
	public final static String FV_EXP_VOUCHER_STATUS = "EXPSTATUS";
	public final static String FV_ENQUIRY_TYPE = "ENQTYPE";
	public final static String FV_SPITCH_TYPE = "PITCHTYPE";
	public final static String FV_DOC_TYPE = "DOCTYPE";;
	public final static String FV_PROMO_TYPE = "PROMOTYPE";;
	
	public final static class PROMOTYPE {
		public final static String CROSSSELL ="CRSSSELL";
		public final static String BUNDLING ="BNDL";
		public final static String UPSELLING ="UPSELL";
		public final static String PLAINDISCOUNT ="PLNDISC";
	}
	
	public final static class RELDATE {
		public final static String TODAY ="TODAY";
		public final static String LASTWEEK ="LSTWEEK";
		public final static String LASTMONTH ="LSTMNTH";
		public final static String LASTTWOMONTH ="LSTTWOMNTH";
		public final static String LASTTHREEMONTH ="LSTTHRMNTH";
		public final static String LASTSIXMONTH ="LSTSIXMNTH";
		public final static String LASTYEAR ="LSTYR";
		public final static String LASTTWOYEAR ="LSTTWOYR";
		public final static String LASTTHREEYEAR ="LSTTHRYR";
	}
	
	public final static class ROLETYPE {
		public final static String SYSADMIN = "SYSADMIN";
		public final static String CORPADMIN = "CORPADMIN";
		public final static String MANAGER = "MANAGER";
		public final static String SALESASSOCIATE = "SALESASS";
		public final static String OFFICEASSOCIATE = "OFFASSOC";
		public final static String FLOORWORK = "FLOORWRK";
	}
		
	public final static class USERTYPE {
		public final static String ALLUSERS = "ALLUSR";
		public final static String MANAGERUSERS = "MGRUSR";
		public final static String ADMINUSERS = "ADMUSR";
		
	}
	public final static class SALESPFTYPE{
		public final static String CATEGORY = "SPFCATG";
		public final static String PRODUCT = "SPFPROD";
		public final static String BRAND = "SPFBRAND";
		public final static String ITEM = "SPFITEM";
	}
	
	public final static class SALESCYCLE_STATUS {
		public final static String INITIATED = "INIT";
		public final static String ASSIGNED = "ASSGND";
		public final static String IN_PROGRESS = "INPRG";
		public final static String NEGOTIATING = "NGTD";
		public final static String CLOSED = "CLSD";
		public final static String FAILED = "FLD";
	}
	
	public final static class FOLLOWUP_RESULT {
		public final static String SOLD = "SOLD";
		public final static String PENDING = "SLPENDING";
		public final static String NOSALE = "NOSALE";
		public final static String PARTSALE = "PARTSALE";
	}
	
	public final static class ITEM_CLASS {
		public final static String TOP_END = "TOPEND";
		public final static String UPPER_MEDIUM = "UPMEDIUM";
		public final static String LOWER_MEDIUM = "LWMEDIUM";
		public final static String ECONOMIC = "ECONOMIC";
	}
	
	public final static class ALERT_STATUS {
		public final static String OPEN = "ALTOPN";
		public final static String ACKNOWLEDGED = "ALTACK";
		public final static String VOID = "ALTVOID";
		public final static String EXPIRED = "ALTEXPRD";
	}
	
	public final static class ALERT_TYPE {
		public final static String SALESLEAD = "ALTSLSLEAD";
		public final static String FOLLOWUP = "ALTFLLWUP";
		public final static String INVSHORTAGE = "ALTINVSHRT";
		public final static String APPOINTMENT = "ALTAPP";
		public final static String SLSPERSTART = "ALTSPBEGIN";
		public final static String SLSPEREND = "ALTSPEND";
		public final static String DISTOR = "ALTDO";
	}
	
	public final static class ADDRESS_TYPE {
		public final static String BILLING = "BLLNG";
		public final static String PRIMARY_SHIPPING = "SHPPRM";
		public final static String SECONDARY_SHIPPING = "SHPSEC";
	}
	
	public final static class VALUE_TYPE {
		public final static String NUMERIC = "NUMER";
		public final static String STRING = "STR";
		public final static String FINITE_VALUE = "FINVAL";
		public final static String BOOLEAN = "BOOL";
	}
	
	public final static class DO_STATUS {
		public final static String RELEASED = "RLSD";
		public final static String PICKING = "PICKNG";
		public final static String PICKED = "PICKD";
		public final static String PACKING = "PACKNG";
		public final static String PACKD = "PACKD";
		public final static String SHIPPING = "SHPPNG";
		public final static String SHIPPD = "SHPPD";
		public final static String DELIVERED = "DLVD";
	}
	
	public final static class EXP_VOUCHER_STATUS { 
		public final static String REQUESTED = "EXPREQ";
		public final static String COUNTERED = "EXPCONTRD";
		public final static String APPROVED = "EXPAPPR";
		public final static String REJECTED = "EXPREJEC";
		public final static String PENDING = "EXPPEN";
		public final static String REREQUESTED = "EXPREREQ";
		public final static String CLOSED = "EXPPDCLSD";
		public final static String REJECTEDCLOSED = "EXPRJCLSD";

	}
	
	
}

