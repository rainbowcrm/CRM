package com.rainbow.crm.saleslead.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface ISalesLeadService extends ITransactionService{

	public int getItemSaleQuantity(Sku item, Date from, Date to,Division division ) ;
	
	public List<RadsError> startSalesCycle(SalesLead salesLead) ;
	
	public List<RadsError> sendEmail(SalesLead salesLead,CRMContext context,String realPath) ;
	
	public SalesLeadExtended getSalesLeadWithExtension( int leadId,CRMContext context) ;
	
	
	public byte[] printQuotation(SalesLead lead) ;

}
