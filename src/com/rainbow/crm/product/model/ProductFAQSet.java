package com.rainbow.crm.product.model;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;

public class ProductFAQSet extends CRMBusinessModelObject{
	
	List<ProductFAQ> productFAQs;
	Product product ;
	
	public List<ProductFAQ> getProductFAQs() {
		return productFAQs;
	}
	
	public void setProductFAQs(List<ProductFAQ> productFAQs) {
		this.productFAQs = productFAQs;
	}
	
	public void addProductFAQ(ProductFAQ productFAQ)
	{
		if(productFAQs == null)
			productFAQs =new ArrayList<ProductFAQ> ();
		productFAQs.add(productFAQ);
		
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	

}
