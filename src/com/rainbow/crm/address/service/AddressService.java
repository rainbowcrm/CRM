package com.rainbow.crm.address.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.address.dao.AddressDAO;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.address.validator.AddressValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

public class AddressService extends AbstractService implements IAddressService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("Address",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context) {
		return super.listData("Address", from, to, whereCondition, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Address)object).setCompany(company);
		AddressValidator validator = new AddressValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Address)object).setCompany(company);
		AddressValidator validator = new AddressValidator(context);
		return validator.validateforUpdate(object);
	}

	/**
	@Override
	public Address getByCode(int company, String code) {
		return ((AddressDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Address getByName(int company, String name) {
		return ((AddressDAO)getDAO()).findByName(company, name);
	}**/
	
	
	

	@Override
	protected ORMDAO getDAO() {
		return (AddressDAO) SpringObjectFactory.INSTANCE.getInstance("AddressDAO");
	}

	
	
	

}
