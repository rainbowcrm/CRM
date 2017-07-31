package com.rainbow.crm.product.service;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.ItemUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.product.dao.ProductDAO;
import com.rainbow.crm.product.dao.ProductFAQDAO;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductFAQ;
import com.rainbow.crm.product.model.ProductFAQSet;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;

public class ProductFAQService extends AbstractionTransactionService implements IProductFAQService{

	
	
	@Override
	public ProductFAQSet getByProduct(Product product, CRMContext context) {
		ProductFAQDAO  dao = (ProductFAQDAO) getDAO();
		IProductService productService = (IProductService) SpringObjectFactory.INSTANCE.getInstance("IProductService");
		product = (Product)productService.getByBusinessKey(product, context);
		ProductFAQSet faqSet = new ProductFAQSet();
		List<ProductFAQ> faqs =   dao.getByProductId(product.getId()) ;
		faqSet.setProductFAQs(faqs);
		faqSet.setCompany(CommonUtil.getCompany(context.getLoggedinCompany()));
		faqSet.setProduct(product);
		return faqSet;
	}

	@Override
	protected String getTableName() {
		return null;
	}

	@Override
	protected ORMDAO getDAO() {
		return (ProductFAQDAO) SpringObjectFactory.INSTANCE.getInstance("ProductFAQDAO");
	}

	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		List<RadsError> errors= new ArrayList<RadsError> ();
		Externalize externalize = new Externalize();
		ProductFAQSet productFAQSet = (ProductFAQSet) object ;
		Product product = ItemUtil.getProduct(context, productFAQSet.getProduct());
		productFAQSet.getProductFAQs().forEach(productFAQ ->  {
			productFAQ.setCompany(CommonUtil.getCompany(context.getLoggedinCompany()));
			if (product == null) {
				errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Product"))) ;
			}else 
				productFAQ.setProduct(product);
			
			User user = CommonUtil.getUser(context, productFAQ.getAuthor());
			if (user == null) {
				errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Product"))) ;
			}else
				productFAQ.setAuthor(user);
			
		}  );
		return errors;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalRecordCount(CRMContext context, String whereCondition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getById(Object PK) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CRMModelObject getByBusinessKey(CRMModelObject object,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		ProductFAQSet fset = (ProductFAQSet) object ;
		adaptfromUI(context, fset);
		return batchUpdateFAQS( (fset.getProductFAQs()),context);
	}

	public TransactionResult batchUpdateFAQS(List<? extends CRMModelObject> objects,
			CRMContext context) {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		int lineNo = 1 ; 
		try {
			for (CRMModelObject object : objects ) {
				int id = GeneralSQLs.getNextPKValue( "Product_FAQs") ;
				((ProductFAQ)object).setId(id);
				((ProductFAQ)object).setLineNumber(lineNo ++ );
				object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
				object.setLastUpdateUser(context.getUser());
			}
			getDAO().batchUpdate(objects);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}
	
	@Override
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			for (CRMModelObject object : objects ) {
				object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
				object.setLastUpdateUser(context.getUser());
			}
			getDAO().batchUpdate(objects);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
