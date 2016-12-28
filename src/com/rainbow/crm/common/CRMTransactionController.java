package com.rainbow.crm.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public abstract class CRMTransactionController extends TransactionController {

	
	public abstract ITransactionService getService() ;

	@Override
	public List<RadsError> adaptfromUI(ModelObject modelObject) {
		return  getService().adaptfromUI((CRMContext)getContext(),(Sales) object);
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
	
	@Override
	public void init(HttpServletRequest request) {
		Object obj  = request.getParameter("id") ;
		if (obj != null && Utils.isPositiveInt(String.valueOf(obj)))  {
			int id = Integer.parseInt(String.valueOf(obj));
			((CRMBusinessModelObject)object).setId(id);
		}
		super.init(request);
	}

	
	@Override
	public List<RadsError> validateforCreate() {
		return getService().validateforCreate((CRMModelObject)object, (CRMContext)getContext());
	}

	
	@Override
	public List<RadsError> validateforUpdate() {
		return getService().validateforUpdate((Sales)object, (CRMContext)getContext());
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
	

	@Override
	public PageResult print() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelObject populateFullObjectfromPK(ModelObject objects) {
		return (ModelObject) getService().getById(object.getPK());
	}

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
