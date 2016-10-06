package com.rainbow.crm.common.ajaxservices;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.filter.dao.CRMFilterDAO;
import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.filter.model.CRMFilterDetails;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

public class FilterAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,IRadsContext ctx) {
		JSONObject json = new JSONObject();
		try { 
		String filterId=searchFields.get("filterId");
		CRMFilter filter =(CRMFilter) CRMFilterDAO.INSTANCE.getById(filterId);
		json.put("FilterName", filter.getName());
		for (CRMFilterDetails det : filter.getDetails()) {
			json.put(det.getField(), det.getValue());
		}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return json.toString();
	}
	
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}

	
	

}
