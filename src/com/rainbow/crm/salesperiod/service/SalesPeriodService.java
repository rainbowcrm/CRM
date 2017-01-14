package com.rainbow.crm.salesperiod.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.salesperiod.dao.SalesPeriodDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.salesperiod.validator.SalesPeriodErrorCodes;
import com.rainbow.crm.salesperiod.validator.SalesPeriodValidator;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class SalesPeriodService extends AbstractionTransactionService implements ISalesPeriodService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("SalesPeriod",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context) {
		return super.listData("SalesPeriod", from, to, whereCondition, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesPeriod)object).setCompany(company);
		SalesPeriodValidator validator = new SalesPeriodValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesPeriod)object).setCompany(company);
		SalesPeriodValidator validator = new SalesPeriodValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new SalesPeriodDAO();
	return (SalesPeriodDAO) SpringObjectFactory.INSTANCE.getInstance("SalesPeriodDAO");
	}

	
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context, (SalesPeriod)object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,SalesPeriod object) {
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
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		Externalize externalize = new Externalize(); ;
		
		if(!Utils.isNullSet(object.getSalesPeriodLines())){
			int lineNo=1;
			for (SalesPeriodLine line: object.getSalesPeriodLines()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getItem() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
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
		SalesPeriod salesPeriod = (SalesPeriod)object ;
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodLines())) {
			int pk = GeneralSQLs.getNextPKValue("SalesPeriods") ;
			salesPeriod.setId(pk);
			for (SalesPeriodLine  line : salesPeriod.getSalesPeriodLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "SalesPeriod_Lines") ;
				line.setId(linePK);
				line.setSalesPeriodDoc(salesPeriod);
			}
		}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		SalesPeriod salesPeriod = (SalesPeriod)object ;
		SalesPeriod oldObject = (SalesPeriod)getById(salesPeriod.getPK());
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodLines())) {
			int  ct = 0;
			Iterator it = oldObject.getSalesPeriodLines().iterator() ;
			for (SalesPeriodLine  line : salesPeriod.getSalesPeriodLines()) {
				SalesPeriodLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (SalesPeriodLine) it.next() ;
				}
				line.setSalesPeriodDoc(salesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SalesPeriod_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				SalesPeriodLine oldLine= (SalesPeriodLine) it.next() ;
				oldLine.setVoided(true);
				salesPeriod.addSalesPeriodLine(oldLine);
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
	public List<SalesPeriod> getStartingSalesPeriodsforAlerts(Date startDt) {
		SalesPeriodDAO dao = (SalesPeriodDAO) getDAO();
		return dao.getStartingSalesPeriodsforAlerts(startDt);
	}

	@Override
	public List<SalesPeriod> getEndSalesPeriodsforAlerts(Date endDt) {
		SalesPeriodDAO dao = (SalesPeriodDAO) getDAO();
		return dao.getEndingSalesPeriodsforAlerts(endDt);
	}
	
	
	
	
}
