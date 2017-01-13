
package com.rainbow.crm.salesportfolio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
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
import com.rainbow.crm.salesportfolio.dao.SalesPortfolioDAO;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.salesportfolio.model.SalesPortfolioLine;
import com.rainbow.crm.salesportfolio.validator.SalesPortfolioErrorCodes;
import com.rainbow.crm.salesportfolio.validator.SalesPortfolioValidator;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class SalesPortfolioService extends AbstractService implements ISalesPortfolioService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("SalesPortfolio",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context) {
		return super.listData("SalesPortfolio", from, to, whereCondition, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesPortfolio)object).setCompany(company);
		SalesPortfolioValidator validator = new SalesPortfolioValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesPortfolio)object).setCompany(company);
		SalesPortfolioValidator validator = new SalesPortfolioValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new SalesPortfolioDAO();
	return (SalesPortfolioDAO) SpringObjectFactory.INSTANCE.getInstance("SalesPortfolioDAO");
	}

	
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context, (SalesPortfolio)object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,SalesPortfolio object) {
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
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPortfolioErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		Externalize externalize = new Externalize(); ;
		/*
		if(!Utils.isNullSet(object.getSalesPortfolioLines())){
			int lineNo=1;
			for (SalesPortfolioLine line: object.getSalesPortfolioLines()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getItem() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPortfolioErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getItem().getName() ;
					IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
					Item item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setItem(item);
				}
			}
		}*/
		return ans;
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		SalesPortfolio salesPortfolio = (SalesPortfolio)object ;
		if (!Utils.isNullSet(salesPortfolio.getSalesPortfolioLines())) {
			int pk = GeneralSQLs.getNextPKValue("SalesPortfolios") ;
			salesPortfolio.setId(pk);
			for (SalesPortfolioLine  line : salesPortfolio.getSalesPortfolioLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "SalesPortfolio_Lines") ;
				line.setId(linePK);
				line.setSalesPortfolioDoc(salesPortfolio);
			}
		}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		SalesPortfolio salesPortfolio = (SalesPortfolio)object ;
		SalesPortfolio oldObject = (SalesPortfolio)getById(salesPortfolio.getPK());
		if (!Utils.isNullSet(salesPortfolio.getSalesPortfolioLines())) {
			int  ct = 0;
			Iterator it = oldObject.getSalesPortfolioLines().iterator() ;
			for (SalesPortfolioLine  line : salesPortfolio.getSalesPortfolioLines()) {
				SalesPortfolioLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (SalesPortfolioLine) it.next() ;
				}
				line.setSalesPortfolioDoc(salesPortfolio);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SAPORTFOLIO_LINES") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				SalesPortfolioLine oldLine= (SalesPortfolioLine) it.next() ;
				oldLine.setVoided(true);
				salesPortfolio.addSalesPortfolioLine(oldLine);
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


	
	
	
}
