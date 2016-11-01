package com.rainbow.crm.product.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public class ProductAnalyzer extends CRMModelObject{
	
	PieChartData salesData  ;
	Product product ;
	Date fromDate;
	Date toDate;
	
	
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public PieChartData getSalesData() {
		return salesData;
	}
	public void setSalesData(PieChartData salesData) {
		this.salesData = salesData;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
	

}
