package com.rainbow.crm.followup.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.followup.dao.FollowupDAO;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.validator.FollowupValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

public class FollowupService extends AbstractService implements IFollowupService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("Followup",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context) {
		return super.listData("Followup", from, to, whereCondition, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Followup)object).setCompany(company);
		FollowupValidator validator = new FollowupValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Followup)object).setCompany(company);
		FollowupValidator validator = new FollowupValidator(context);
		return validator.validateforUpdate(object);
	}

	/**
	@Override
	public Followup getByCode(int company, String code) {
		return ((FollowupDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Followup getByName(int company, String name) {
		return ((FollowupDAO)getDAO()).findByName(company, name);
	}**/
	
	
	

	@Override
	protected ORMDAO getDAO() {
		return (FollowupDAO) SpringObjectFactory.INSTANCE.getInstance("FollowupDAO");
	}


	
	

}
