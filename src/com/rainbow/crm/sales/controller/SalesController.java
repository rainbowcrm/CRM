package com.rainbow.crm.sales.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesController extends CRMTransactionController{
	
		
	
	public ISalesService getService() {
		ISalesService serv = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		return serv;
	}


	public Map <String, String > getDeliveryModes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_DELIVERY_MODE);
		return ans;
	}
	
	public Map <String, String > getOrderTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ORDERTYPE);
		return ans;
	}
	

	
	public Map <String, String > getAllDivisions() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		IDivisionService service =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(((CRMContext)getContext()).getLoggedinCompany());
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}

}
