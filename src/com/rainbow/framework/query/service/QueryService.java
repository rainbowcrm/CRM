package com.rainbow.framework.query.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.customer.dao.CustomerDAO;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.framework.query.dao.QueryDAO;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryRecord;
import com.rainbow.framework.query.model.QueryReport;
import com.rainbow.framework.query.validation.QueryValidator;
import com.rainbow.framework.setup.model.EntityField;
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
		return generateReport(query,lst);
	}

	private QueryReport generateReport(Query query, List  list) {
		QueryReport report = new QueryReport();
		report.setTitles(query.getSelectedFields());
		report.setFrom(query.getFromDate().toLocaleString());
		report.setTo(query.getToDate().toLocaleString());
		if(!Utils.isNullList(list)) {
			for(int i = 0 ; i < list.size() ; i ++ ) {
				QueryRecord record= new QueryRecord();
				Object [] objects  = (Object [])list.get(i);
				String  []actValues = new String[objects.length];
				for (int j = 0 ; j < objects.length ; j ++ ) {
					actValues[j]=String.valueOf(objects[j]);
				}
				record.setFields(actValues);
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
		Set<String> joins= new LinkedHashSet<String>();
		Set<String> conditions= new LinkedHashSet<String>();
		for (int i = 0 ; i  < query.getSelectedFields().length ; i ++ )  {
			
			EntityField field = MetadataSQL.getEntityField(query.getEntity(),query.getSelectedFields()[i]);
			if(!Utils.isNullString(field.getHqljoinClause())) { 
				  joins.add(field.getHqljoinClause() + " " );
				  conditions.add( field.getJoinCondition()) ;
			}
			 selectFields.append( " " + query.getSelectedFields()[i] + " ");
			 if (i < query.getSelectedFields().length-1) 
				 selectFields.append(",") ;
		}
		
		selectFields.append(" from " + query.getEntity()+ " " + query.getEntity()) ;
		joins.forEach( hqlClause  ->  { 
			selectFields.append(" " +  hqlClause + " ");
		} );
		
		Metadata metadata = MetadataSQL.getMetaDataforEntity(query.getEntity());
		if (!Utils.isNullString( metadata.getDateField()))
			selectFields.append(" where " + query.getEntity() + "."+ metadata.getDateField()  + " >= :fromDate "+ 
					" and  "   +  query.getEntity() + "."+ metadata.getDateField() + " <= :toDate  and  " + query.getEntity() + ".company.id =:company and ");
		else
			selectFields.append(" where  " + query.getEntity() + ".company.id =:company and ");
	
		/*conditions.forEach( condition  ->  { 
			selectFields.append(" " +  condition + " and ");
		} );*/
		
		query.getConditions().forEach( condition ->  {  
			selectFields.append( condition.toString() );
		});
		if(!Utils.isNullString(query.getSortField())) {
				selectFields.append(" order by " +query.getSortField() + " "  + query.getSortDesc());
		}
		return selectFields.toString();
	}

	@Override
	public String getVelocityConverted(QueryReport report, CRMContext context) {
		VelocityEngine ve = new VelocityEngine();
		Externalize externalize = new Externalize();
        try {
        User user = context.getLoggedInUser();
        Company company= CommonUtil.getCompany(context.getLoggedinCompany());
        String path = CRMAppConfig.INSTANCE.getProperty("VelocityTemplatePath");
        ve.setProperty("file.resource.loader.path", path);
        ve.init();
        Template t = ve.getTemplate("queryResult.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("companyName", company.getName());
        velocityContext.put("title", externalize.externalize(context, "Query_Result"));
        velocityContext.put("runon", externalize.externalize(context, "Run_on"));
        velocityContext.put("FromText", externalize.externalize(context, "From"));
        velocityContext.put("ToText", externalize.externalize(context, "To"));
        velocityContext.put("FromDate", report.getFrom());
        velocityContext.put("ToDate", report.getTo());
        velocityContext.put("runDate", new java.util.Date().toLocaleString());
        
        for(int i = 1 ; i <= 7 ; i ++){
        	if (report.getTitles().length >= i)
        		velocityContext.put("Field"+i,Utils.isNullString(report.getTitles()[i-1])?" ":report.getTitles()[i-1]);
        	else
        		velocityContext.put("Field"+i," ");
        }
        velocityContext.put("records", report.getRecords());
        
        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        String content=  writer.toString();
        return content;
        
        } catch(Exception ex){
        	Logwriter.INSTANCE.error(ex);
        }

        return "";
	}
	
	
	

}
