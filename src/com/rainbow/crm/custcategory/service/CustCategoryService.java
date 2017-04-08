package com.rainbow.crm.custcategory.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;







import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.custcategory.dao.CustCategoryDAO;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.custcategory.validator.CustCategoryValidator;
import com.rainbow.crm.customer.model.Customer;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class CustCategoryService extends AbstractService implements ICustCategoryService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("CustCategory",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("CustCategory", from, to, whereCondition, context,sortCriteria);
	}
	
	

	@Override
	public List<Customer> checCustomers(CustCategory custCategory) {
		
		custCategory.getConditions().forEach( condition ->  {  
			selectFields.append( condition.toString() );
		});
		
		return null;
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((CustCategory)object).setCompany(company);
		CustCategoryValidator validator = new CustCategoryValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((CustCategory)object).setCompany(company);
		CustCategoryValidator validator = new CustCategoryValidator(context);
		return validator.validateforUpdate(object);
	}


	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return null;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		return null;
	}

	@Override
	protected ORMDAO getDAO() {
		return (CustCategoryDAO) SpringObjectFactory.INSTANCE.getInstance("CustCategoryDAO");
	}

	

}
