package com.rainbow.crm.enquiry.controller;



import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.enquiry.service.IEnquiryService;
import com.rainbow.crm.database.GeneralSQLs;

public class EnquiryController extends CRMCRUDController{
	
	public IBusinessService getService() {
		IEnquiryService serv = (IEnquiryService) SpringObjectFactory.INSTANCE.getInstance("IEnquiryService");
		return serv;
	}

	public Map <String, String > getCommunicationModes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_COMMUNICATION_MODE);
		return ans;
	}
	
	public Map <String, String > getConfidenceLevels() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_CONFIDENCE_LEVEL);
		return ans;
	}
	
	public Map <String, String > getEnquiryResults() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_FOLLOWUP_RESULT);
		return ans;
	}
	public Map <String, String > getSuccessReasons() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_SUCCESS_REASON);
		return ans;
	}
	public Map <String, String > getFailureReasons() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_FAILURE_REASON);
		return ans;
	}
	public Map <String, String > getAllReasons() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_SUCCESS_REASON);
		Map<String, String> ans1 = GeneralSQLs.getFiniteValues(CRMConstants.FV_FAILURE_REASON);
		ans.putAll(ans1);
		return ans;
	}
}
