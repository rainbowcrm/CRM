package com.rainbow.crm.sales.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface ISalesService extends ITransactionService{
	
	public int getItemSaleQuantity(Item item, Date from, Date to,Division division ) ;

}
