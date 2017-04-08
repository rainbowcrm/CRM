package com.rainbow.crm.custcategory.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.customer.model.Customer;

public interface ICustCategoryService extends ITransactionService{
	
  public List<Customer> checCustomers(CustCategory custCategory)l
	
}
