package com.rainbow.framework.query.service;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.dao.CustomerDAO;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.framework.query.dao.QueryDAO;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryReport;
import com.rainbow.framework.query.validation.QueryValidator;
import com.rainbow.framework.setup.model.Metadata;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

public class QueryService implements IQueryService{

	@Override
	public QueryReport getResult(Query query, CRMContext context) {
		String queryString = makeQuery(query);
		QueryDAO dao = (QueryDAO)getDAO();
		dao.getQueryRecord(queryString, context.getLoggedinCompany());
		return null;
	}

	@Override
	public List<RadsError> validate(Query query, CRMContext context) {
	   List<RadsError> errors = new ArrayList< > ();
		QueryValidator validator = new QueryValidator();
		return validator.validateforCreate(query);
	}
	
	
	protected ORMDAO getDAO() {
		return (QueryDAO) SpringObjectFactory.INSTANCE.getInstance("QueryDAO");
	}
	private String makeQuery(Query query) {
		StringBuffer selectFields = new StringBuffer (" select ");
		
		for (int i = 0 ; i  < query.getSelectedFields().length ; i ++ )  {
			 selectFields.append( " " + query.getSelectedFields()[i] + " ");
			 if (i < query.getSelectedFields().length-1) 
				 selectFields.append(",") ;
		}
		
		selectFields.append(" from " + query.getEntity()) ;
		Metadata metadata = MetadataSQL.getMetaDataforEntity(query.getEntity());
		selectFields.append(" where " + metadata.getDateField()  + " >= " + query.getFromDate() + 
				" and  "   + metadata.getDateField() + " <= " +  query.getToDate() +  " and company.id =:company ");
		
		query.getConditions().forEach( condition ->  {  
			selectFields.append( condition.toString() );
		});
		if(!Utils.isNullString(query.getSortField())) {
				selectFields.append(" order by " +query.getSortField() + " "  + query.getSortDesc());
		}
		return selectFields.toString();
	}
	

}
