package com.rainbow.crm.document.service;

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
import com.rainbow.crm.document.dao.DocumentDAO;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.validator.DocumentValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class DocumentService extends AbstractService implements IDocumentService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("Document",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Document", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Document)object).setCompany(company);
		DocumentValidator validator = new DocumentValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Document)object).setCompany(company);
		DocumentValidator validator = new DocumentValidator(context);
		return validator.validateforUpdate(object);
	}


	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Document document = (Document) object ;
		TransactionResult result=  super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Document document = (Document) object ;
		TransactionResult result= super.update(object, context);
		return result; 
	}

	@Override
	protected ORMDAO getDAO() {
		return (DocumentDAO) SpringObjectFactory.INSTANCE.getInstance("DocumentDAO");
	}

	


	
	

}
