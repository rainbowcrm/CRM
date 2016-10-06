package com.rainbow.crm.saleslead.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.saleslead.dao.SalesLeadDAO;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.validator.SalesLeadErrorCodes;
import com.rainbow.crm.saleslead.validator.SalesLeadValidator;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class SalesLeadService extends AbstractService implements ISalesLeadService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("SalesLead",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context) {
		return super.listData("SalesLead", from, to, whereCondition, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesLead)object).setCompany(company);
		SalesLeadValidator validator = new SalesLeadValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesLead)object).setCompany(company);
		SalesLeadValidator validator = new SalesLeadValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new SalesLeadDAO();
	return (SalesLeadDAO) SpringObjectFactory.INSTANCE.getInstance("SalesLeadDAO");
	}

	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context,ModelObject obj) {
		SalesLead object = (SalesLead) obj;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
		List<RadsError> ans = new ArrayList<RadsError>();
		
		
		if (object.getDivision() != null) {
			int divisionId  = object.getDivision().getId() ;
			IDivisionService divisionService =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0 )
				division = (Division)divisionService.getById(divisionId);
			else
				division  = (Division)divisionService.getByBusinessKey(object.getDivision(), context);
			if(division == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		if (object.getCustomer() != null) {
			String phone = object.getCustomer().getPhone();
			ICustomerService customerService = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
			Customer customer = customerService.getByPhone(context.getLoggedinCompany(), phone);
			if (customer != null)
				 object.setCustomer(customer);
		}
		Externalize externalize = new Externalize(); ;
		
		if(!Utils.isNullSet(object.getSalesLeadLines())){
			int lineNo=1;
			for (SalesLeadLine line: object.getSalesLeadLines()) {
				line.setCompany(company);
				line.setDocNumber(object.getDocNumber());
				line.setDivision(object.getDivision());
				line.setLineNumber(lineNo ++);
				if(line.getItem() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getItem().getName() ;
					IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
					Item item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setItem(item);
				}
			}
		}
		return ans;
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		SalesLead salesLead = (SalesLead)object ;
		if (Utils.isNull(salesLead.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("SalesLead", context, salesLead.getDivision());
			salesLead.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(salesLead.getSalesLeadLines())) {
			int pk = GeneralSQLs.getNextPKValue("SalesLead") ;
			salesLead.setId(pk);
			for (SalesLeadLine  line : salesLead.getSalesLeadLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "SalesLead_Lines") ;
				line.setId(linePK);
				line.setSalesLeadDoc(salesLead);
				line.setDocNumber(salesLead.getDocNumber());
			}
		}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		SalesLead salesLead = (SalesLead)object ;
		SalesLead oldObject = (SalesLead)getById(salesLead.getPK());
		//SalesLead oldInvObj = (SalesLead)oldObject.clone();
		if (!Utils.isNullSet(salesLead.getSalesLeadLines())) {
			int  ct = 0;
			Iterator it = oldObject.getSalesLeadLines().iterator() ;
			for (SalesLeadLine  line : salesLead.getSalesLeadLines()) {
				SalesLeadLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (SalesLeadLine) it.next() ;
				}
				line.setSalesLeadDoc(salesLead);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SalesLead_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				SalesLeadLine oldLine= (SalesLeadLine) it.next() ;
				oldLine.setVoided(true);
				salesLead.addSalesLeadLine(oldLine);
			}
		}
		return super.update(object, context);
	}

	@Override
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchUpdate(objects, context);
	}

	@Override
	public TransactionResult batchCreate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchCreate(objects, context);
	}

	@Override
	public int getItemSaleQuantity(Item item, Date from, Date to,Division division) {
		//SalesLeadDAO dao = (SalesLeadDAO)getDAO() ;
		return GeneralSQLs.getItemSoldQty(item.getId(),from,to,division.getId());
	}

	@Override
	public List<RadsError> startSalesCycle(SalesLead salesLead) {
		String k = CRMConstants.SALESCYCLE_STATUS.INITIATED;
		FiniteValue value = new FiniteValue();
		value.setCode(CRMConstants.SALESCYCLE_STATUS.INITIATED);
		value.setType("SLCYCSTS");
		salesLead.setStatus(value);
		getDAO().update(salesLead);
		return null;
	}
	
	
	
	
	
}
