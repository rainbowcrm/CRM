package com.rainbow.crm.promotion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.promotion.dao.PromotionDAO;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.sql.PromotionSQLs;
import com.rainbow.crm.promotion.validator.PromotionValidator;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryCondition;
import com.rainbow.framework.query.model.QueryRecord;
import com.rainbow.framework.query.model.QueryReport;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class PromotionService extends AbstractService implements
		IPromotionService {

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("Promotion", context);
	}

	@Override
	public Object getById(Object PK) {
		Promotion category = (Promotion) getDAO().getById(PK);
		return category;
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Promotion", from, to, whereCondition, context,
				sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		PromotionValidator validator = new PromotionValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE
				.getInstance("ICompanyService");
		Company company = (Company) compService.getById(context
				.getLoggedinCompany());
		((Promotion) object).setCompany(company);
		PromotionValidator validator = new PromotionValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		Promotion promotion = (Promotion) object;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE
				.getInstance("ICompanyService");
		Company company = (Company) compService.getById(context
				.getLoggedinCompany());
		Division division = CommonUtil.getDivisionObect(context,
				promotion.getDivision());
		promotion.setDivision(division);
		((Promotion) object).setCompany(company);

		return null;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		return null;
	}

	@Override
	protected ORMDAO getDAO() {
		return (PromotionDAO) SpringObjectFactory.INSTANCE
				.getInstance("PromotionDAO");
	}

	@Transactional
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		List<RadsError> errors = new ArrayList<RadsError>();
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			object.setLastUpdateDate(new java.sql.Timestamp(
					new java.util.Date().getTime()));
			object.setLastUpdateUser(context.getUser());
			// ((PromotionDAO)getDAO()).deleteOrphanedRecords((Promotion)object);
			getDAO().update(object);
		} catch (DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),
					CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE;
			throw new CRMDBException(error);
		}
		return new TransactionResult(result, errors);
	}

}
