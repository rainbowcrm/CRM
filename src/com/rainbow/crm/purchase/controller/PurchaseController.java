package com.rainbow.crm.purchase.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.purchase.model.Purchase;
import com.rainbow.crm.purchase.service.IPurchaseService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class PurchaseController extends TransactionController{
	
	@Override
	public ModelObject populateFullObjectfromPK(ModelObject objects) {
		return (ModelObject) getService().getById(object.getPK());
	}

	
	@Override
	public List<RadsError> adaptfromUI(ModelObject modelObject) {
		return  getService().adaptfromUI((CRMContext)getContext(),(Purchase) object);
	}

	@Override
	public List<RadsError> adapttoUI(ModelObject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforCancel() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPurchaseService getService() {
		IPurchaseService serv = (IPurchaseService) SpringObjectFactory.INSTANCE.getInstance("IPurchaseService");
		return serv;
	}

	@Override
	public List<RadsError> validateforCreate() {
		return getService().validateforCreate((Purchase)object, (CRMContext)getContext());
	}

	
	/*@Override
	public List<RadsError> validateforUpdate() {
		return getService().validateforUpdate((Purchase)object, (CRMContext)getContext());
	}

	@Override
	public List<RadsError> validateforDelete() {
		return null;
	}*/

	@Override
	public List<RadsError> validateforUpdate() {
		return getService().validateforUpdate((Purchase)object, (CRMContext)getContext());
	}

	@Override
	public PageResult create() {
			return new PageResult(getService().create((CRMModelObject)object, (CRMContext)getContext()));
	}
	
	@Override
	public PageResult update() {
		return new PageResult(getService().update((CRMModelObject)object, (CRMContext)getContext()));
	}

	@Override
	public PageResult delete() {
		return null;
	}

	@Override
	public void read() {
		ModelObject thisObject = getService().getByBusinessKey((CRMModelObject)object, (CRMContext)getContext());
		setObject(thisObject);
	}

	/*@Override
	public PageResult update() {
		return null;
	}*/

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}
	
	@Override
	public IRadsContext generateContext(String authToken) {
		return LoginSQLs.loggedInUser(authToken);
	}
	
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
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
