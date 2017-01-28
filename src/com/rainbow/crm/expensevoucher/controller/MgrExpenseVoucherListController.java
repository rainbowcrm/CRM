package com.rainbow.crm.expensevoucher.controller;

import com.techtrade.rads.framework.filter.Filter;

public class MgrExpenseVoucherListController  extends ExpenseVoucherListController{
	
	protected String getFilter(Filter filterData ) {
		StringBuffer filter =  new StringBuffer (super.getFilter(filterData));
		String prefix = " " ; 
		if (filter.length() < 1) 
			prefix = " where " ;
		else 
			prefix = " and " ;
		filter.append(  prefix +  "  status.code in  ('EXPREQ','EXPREREQ' )" ) ;
		return filter.toString();
	}

}
