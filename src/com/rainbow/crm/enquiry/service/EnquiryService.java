package com.rainbow.crm.enquiry.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;















import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.saleslead.validator.SalesLeadErrorCodes;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.enquiry.dao.EnquiryDAO;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.rainbow.crm.enquiry.model.EnquiryLine;
import com.rainbow.crm.enquiry.validator.EnquiryValidator;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class EnquiryService extends AbstractionTransactionService implements IEnquiryService{



	@Override
	protected String getTableName() {
		return "Enquiry";
	}
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Enquiry", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Enquiry)object).setCompany(company);
		adaptfromUI(context, object);
		EnquiryValidator validator = new EnquiryValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Enquiry)object).setCompany(company);
		adaptfromUI(context, object);
		EnquiryValidator validator = new EnquiryValidator(context);
		return validator.validateforUpdate(object);
	}


	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Enquiry enquiry = (Enquiry) object ;
		if (Utils.isNull(enquiry.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("Enquiry", context,null);
			enquiry.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(enquiry.getEnquiryLines())) {
			int pk = GeneralSQLs.getNextPKValue("Enquiry") ;
			enquiry.setId(pk);
			for (EnquiryLine  line : enquiry.getEnquiryLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "Enqury_Lines") ;
				line.setId(linePK);
				line.setEnquiry(enquiry);
				line.setDocNumber(enquiry.getDocNumber());
			}
		}
		TransactionResult result=  super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Enquiry enquiry = (Enquiry) object ;
		Enquiry oldObject = (Enquiry)getById(enquiry.getPK());
		//SalesLead oldInvObj = (SalesLead)oldObject.clone();
		if (!Utils.isNullSet(enquiry.getEnquiryLines())) {
			int  ct = 0;
			Iterator it = oldObject.getEnquiryLines().iterator() ;
			for (EnquiryLine  line : enquiry.getEnquiryLines()) {
				EnquiryLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (EnquiryLine) it.next() ;
				}
				line.setEnquiry(enquiry);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "Enqury_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				EnquiryLine oldLine= (EnquiryLine) it.next() ;
				oldLine.setDeleted(true);
				enquiry.addEnquiryLine(oldLine);
			}
		}
		TransactionResult result= super.update(object, context);
		return result; 
	}

	@Override
	protected ORMDAO getDAO() {
		return (EnquiryDAO) SpringObjectFactory.INSTANCE.getInstance("EnquiryDAO");
	}


	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		Enquiry enquiry = (Enquiry)object;
		Externalize externalize = new Externalize(); ;
		List<RadsError> ans = new ArrayList<RadsError>();
		if(enquiry.getTerritory() != null) {
			ITerritoryService territoryService = (ITerritoryService)SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
			Territory territory  = (Territory)territoryService.getById(enquiry.getTerritory().getId());
			enquiry.setTerritory(territory);
		}
		if(!Utils.isNullSet(enquiry.getEnquiryLines())){
			int lineNo=1;
			for (EnquiryLine line: enquiry.getEnquiryLines()) {
				line.setCompany(enquiry.getCompany());
				line.setDocNumber(enquiry.getDocNumber());
				line.setLineNumber(lineNo ++);
				if(line.getSku() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getSku().getName() ;
					ISkuService itemService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
					Sku item = itemService.getByName(enquiry.getCompany().getId(), itemName);
					line.setSku(item);
				}
			}
		}
		return super.adaptfromUI(context, object);
	}


	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		return super.adaptToUI(context, object);
	}

	
	
	


	
	

}
