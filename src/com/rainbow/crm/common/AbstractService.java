package com.rainbow.crm.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.hibernate.ORMDAO;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.utils.Utils;

@Transactional(rollbackFor = Exception.class)
public abstract class AbstractService implements IBusinessService{
	
	
	
	
	@Override
	public CRMModelObject getByBusinessKey(CRMModelObject object, CRMContext context) {
		Map<String, Object> keys =  object.getBK();
		StringBuffer condition = new StringBuffer();
		if (keys != null ) {
			condition.append(" where ");
			Iterator it = keys.keySet().iterator();
			while (it.hasNext()) {
				String key = (String)it.next() ;
				Object val = keys.get(key);
				if(val == null ) continue ; 
				if (val instanceof Integer || val instanceof Long  || val instanceof Double || val instanceof Float)
					condition  = condition.append( Utils.initlower(key) + " = " + val ) ;
				else
					condition  = condition.append( Utils.initlower(key) + " = '" + val +"'" ) ;
				if (it.hasNext()) {
					condition.append(" and ") ;
				}
			}
		}
		if (condition.toString().equals(" where ")) return null;
		List<? extends CRMModelObject> objects = listData(0, 2, condition.toString(), context);
		if (!Utils.isNullList(objects))
			return objects.get(0);
		else
			return  null;
	}

	protected abstract ORMDAO getDAO() ;
	
	@Transactional 
	public TransactionResult create(CRMModelObject object,CRMContext context)  {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			object.setObjectVersion(1);
			object.setCreatedDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			object.setCreatedUser(context.getUser());
			getDAO().create(object);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_UNABLE_TO_CREATE);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new RuntimeException(ex) ;
		}
		return new TransactionResult(result,errors);
	}
	
	@Transactional
	public TransactionResult update(CRMModelObject object,CRMContext context) {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			object.setLastUpdateUser(context.getUser());
			getDAO().update(object); 
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}
	
	@Transactional
	public TransactionResult batchUpdate(List<CRMModelObject> objects,CRMContext context)  throws CRMDBException {
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

	@Transactional
	public TransactionResult batchCreate(List<CRMModelObject> objects,CRMContext context)  throws CRMDBException {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			for (CRMModelObject object : objects ) {
				object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
				object.setLastUpdateUser(context.getUser());
			}
			getDAO().batchCreate(objects);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}
	
	public List<CRMModelObject> listData(String className,int from, int to,
			String whereCondition, CRMContext context) {
		 StringBuffer additionalCondition = new StringBuffer();
		 boolean allowAllDiv = CommonUtil.allowAllDivisionAccess(context);
		 if (Utils.isNullString(whereCondition) ){
			 additionalCondition = additionalCondition.append(" where company.id = " +  context.getLoggedinCompany()) ;
		 }else { 
			 additionalCondition = additionalCondition.append(whereCondition +  " and company.id= " +  context.getLoggedinCompany()) ;
		 }
		 
		 return  getDAO().listData(className ,from, to, additionalCondition.toString());

	}
	
	public List<? extends CRMModelObject> listData(String className,int from, int to,
			String whereCondition, String orderBy, CRMContext context) {
		 StringBuffer additionalCondition = new StringBuffer();
		 if (Utils.isNullString(whereCondition) ){
			 additionalCondition = additionalCondition.append(" where company.id = " +  context.getLoggedinCompany()) ;
		 }else { 
			 additionalCondition = additionalCondition.append(whereCondition +  " and company.id= " +  context.getLoggedinCompany()) ;
		 }
		 return  getDAO().listData(className ,from, to, additionalCondition.toString() , orderBy);

	}
	
	@Override
	public List<CRMModelObject> findAll(String className, String whereCondition, String orderBy, CRMContext context)
	{
		StringBuffer additionalCondition = new StringBuffer();
		 if (Utils.isNullString(whereCondition) ){
			 additionalCondition = additionalCondition.append(" where company.id = " +  context.getLoggedinCompany()) ;
		 }else { 
			 additionalCondition = additionalCondition.append(whereCondition +  " and company.id= " +  context.getLoggedinCompany()) ;
		 }
		 return  getDAO().findAll(className , additionalCondition.toString() , orderBy);

		
	}
	
	
}
