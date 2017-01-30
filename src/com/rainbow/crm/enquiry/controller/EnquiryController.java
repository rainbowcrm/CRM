package com.rainbow.crm.enquiry.controller;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.enquiry.service.IEnquiryService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.database.GeneralSQLs;
import com.techtrade.rads.framework.utils.Utils;

public class EnquiryController extends CRMCRUDController{
	
	public IBusinessService getService() {
		IEnquiryService serv = (IEnquiryService) SpringObjectFactory.INSTANCE.getInstance("IEnquiryService");
		return serv;
	}

	public Map <String, String > getEnquiryTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ENQUIRY_TYPE);
		return ans;
	}
	
	public Map <String, String > getAllTerritories() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ITerritoryService service =(ITerritoryService) SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		List<Territory> territorries = (List<Territory>)service.findAll("Territory", "", "territory", (CRMContext)getContext());
		if (!Utils.isNullList(territorries)) {
			for (Territory territory : territorries) {
				ans.put(String.valueOf(territory.getId()), territory.getTerritory());
			}
		}
		return ans;
	}
}
