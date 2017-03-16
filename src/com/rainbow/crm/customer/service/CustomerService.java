package com.rainbow.crm.customer.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.customer.dao.CustomerDAO;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.validator.CustomerValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class CustomerService extends AbstractService implements ICustomerService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("Customer",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Customer", from, to, whereCondition, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Customer)object).setCompany(company);
		CustomerValidator validator = new CustomerValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Customer)object).setCompany(company);
		CustomerValidator validator = new CustomerValidator(context);
		return validator.validateforUpdate(object);
	}

	/**
	@Override
	public Customer getByCode(int company, String code) {
		return ((CustomerDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Customer getByName(int company, String name) {
		return ((CustomerDAO)getDAO()).findByName(company, name);
	}**/
	
	
	

	@Override
	protected ORMDAO getDAO() {
		return (CustomerDAO) SpringObjectFactory.INSTANCE.getInstance("CustomerDAO");
	}

	@Override
	public Customer getByEmail(int company, String email) {
		return ((CustomerDAO)getDAO()).findByEmail(company, email);
	}

	@Override
	public Customer getByPhone(int company, String phone) {
		return ((CustomerDAO)getDAO()).findByPhone(company, phone);
	}
	
	
	

}
