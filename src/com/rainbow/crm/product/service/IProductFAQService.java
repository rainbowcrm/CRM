package com.rainbow.crm.product.service;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductFAQSet;

public interface IProductFAQService extends ITransactionService{
	
	public ProductFAQSet getByProduct(Product product, CRMContext context);

}
