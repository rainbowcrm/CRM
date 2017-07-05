package com.rainbow.crm.followup.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.followup.service.IFollowupService;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.database.GeneralSQLs;

public class FollowupController extends CRMCRUDController{
	
	public IBusinessService getService() {
		IFollowupService serv = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
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
	
	public Map <String, String > getFollowupResults() {
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
		/*Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_SUCCESS_REASON);
		Map<String, String> ans1 = GeneralSQLs.getFiniteValues(CRMConstants.FV_FAILURE_REASON);
		ans.putAll(ans1);
		return ans;*/
		
		IReasonCodeService reasonCodeService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
		Map<String,String > ans = new HashMap<String,String> ();
		List<ReasonCode> positiveReasons = reasonCodeService.getAllReasonsforType(new FiniteValue(CRMConstants.REASON_TYPE.FAVORABLE_SALESLEAD_REASON), (CRMContext) getContext());
		List<ReasonCode> negativeReasons = reasonCodeService.getAllReasonsforType(new FiniteValue(CRMConstants.REASON_TYPE.UNFAVORABLE_SALESLEAD_REASON), (CRMContext) getContext());
		positiveReasons.forEach( reasonCode ->  {
			ans.put(String.valueOf(reasonCode. getId()),reasonCode.getReason());
		});
		negativeReasons.forEach( reasonCode ->  {
			ans.put(String.valueOf(reasonCode. getId()),reasonCode.getReason());
		});

		
		return ans;
		
	}
}
