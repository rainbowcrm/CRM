package com.rainbow.framework.query.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.customer.dao.CustomerDAO;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.framework.query.dao.QueryDAO;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryRecord;
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
		List lst = dao.getQueryRecord(queryString, context.getLoggedinCompany(),query.getFromDate(),query.getToDate());
		return generateReport(lst);
	}

	private QueryReport generateReport(List  list) {
		QueryReport report = new QueryReport();
		if(!Utils.isNullList(list)) {
			for(int i = 0 ; i < list.size() ; i ++ ) {
				QueryRecord record= new QueryRecord();
				Object [] objects  = (Object [])list.get(i);
				//String[] stringArray = Arrays.copyOf(objects, objects.length, String[].class);
				//record.setFields((String [])objects);
				record.setFieldCount(objects.length);
				report.addRecord(record);
			}
		}
		return report;
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
		if (!Utils.isNullString( metadata.getDateField()))
			selectFields.append(" where " + metadata.getDateField()  + " >= :fromDate "+ 
					" and  "   + metadata.getDateField() + " <= :toDate  and company.id =:company and ");
		else
			selectFields.append(" where company.id =:company and ");
		
		query.getConditions().forEach( condition ->  {  
			selectFields.append( condition.toString() );
		});
		if(!Utils.isNullString(query.getSortField())) {
				selectFields.append(" order by " +query.getSortField() + " "  + query.getSortDesc());
		}
		return selectFields.toString();
	}
	

}
