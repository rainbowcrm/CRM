
package com.rainbow.crm.loyalty.service;

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
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.loyalty.dao.LoyaltyDAO;
import com.rainbow.crm.loyalty.model.Loyalty;
import com.rainbow.crm.loyalty.validator.LoyaltyValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class LoyaltyService extends AbstractService implements ILoyaltyService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("Loyalty",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Loyalty", from, to, whereCondition, context,sortCriteria);
	}
	
	

	@Override
	public void addToLoyalty(int salesId,CRMContext context) {
		ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales sales = (Sales) salesService.getById(salesId);
		if(sales.getCustomer() == null) return;
		
		Double amount  = sales.getNetAmount() ;
		String amountforLoyaltySTR = ConfigurationManager.getConfig(ConfigurationManager.SLS_AMOUNT_UNIT_LOYALTY,context);
		if (Utils.isPositiveInt(amountforLoyaltySTR))   {
			Double  amountforLoyalty = Double.parseDouble(amountforLoyaltySTR);
			Double loyatyforSale = amount/amountforLoyalty;
			Loyalty loyalty = new Loyalty();
			loyalty.setCompany(sales.getCompany());
			loyalty.setDivision(sales.getDivision());
			loyalty.setCustomer(sales.getCustomer());
			if(sales.isReturn())  {
				loyalty.setPoints(loyatyforSale *  -1 );
			}else
				loyalty.setPoints(loyatyforSale);
			
			loyalty.setRedeemed(false);
			create(loyalty, context);
			
		}
		
		
		
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Loyalty)object).setCompany(company);
		LoyaltyValidator validator = new LoyaltyValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Loyalty)object).setCompany(company);
		LoyaltyValidator validator = new LoyaltyValidator(context);
		return validator.validateforUpdate(object);
	}


	

	@Override
	protected ORMDAO getDAO() {
		return (LoyaltyDAO) SpringObjectFactory.INSTANCE.getInstance("LoyaltyDAO");
	}

	
	


	
	

}
