package com.rainbow.crm.alert.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMListController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.alert.service.IAlertService;
import com.rainbow.crm.alert.validator.AlertValidator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class AlertListController extends CRMListController{

	@Override
	public IBusinessService getService() {
		IAlertService serv = (IAlertService) SpringObjectFactory.INSTANCE.getInstance("IAlertService");
		return serv;
	}

	@Override
	public Object getPrimaryKeyValue(ModelObject object) {
		Alert alert = (Alert) object ;
		return alert.getId();
	}

	@Override
	public PageResult submit(List<ModelObject> objects, String submitAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforDelete(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforEdit(List<ModelObject> objects) {
		AlertValidator validator = new AlertValidator(((CRMContext)getContext()));
		return validator.eligibleForEdit(objects);
	}

	@Override
	public PageResult goToEdit(List<ModelObject> objects) {
		PageResult result = new PageResult();
		result.setNextPageKey("newalert");
		return result;
	}
	
	public Map <String, String > getTypes() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_ALERT_TYPE));
		return ans;
	}
	
	public Map <String, String > getStatuses() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(GeneralSQLs.getFiniteValues(CRMConstants.FV_ALERT_STATUS));
		return ans;
	}

	
	
}
