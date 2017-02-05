package com.rainbow.framework.query.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryReport;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IQueryService {
	
	public  QueryReport getResult(Query query , CRMContext context);
	
	public List<RadsError> validate (Query query , CRMContext context);
	

}
