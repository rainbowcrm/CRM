package com.rainbow.crm.profile.model;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.techtrade.rads.framework.model.graphdata.GaugeChartData;

public class CustomerProfile extends CRMModelObject{

	Customer customer;
	
	GaugeChartData satisfactionIndex;
	
	
	List<SalesLine> pastSales;
	List<FeedBackLine> feedBackLines;
	List<WishListLine> openWishes;
	List<Document> documents;
	List<SalesLeadLine> salesLeadLines;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<SalesLine> getPastSales() {
		return pastSales;
	}
	public void setPastSales(List<SalesLine> pastSales) {
		this.pastSales = pastSales;
	}
	public List<FeedBackLine> getFeedBackLines() {
		return feedBackLines;
	}
	public void setFeedBackLines(List<FeedBackLine> feedBackLines) {
		this.feedBackLines = feedBackLines;
	}
	public List<WishListLine> getOpenWishes() {
		return openWishes;
	}
	public void setOpenWishes(List<WishListLine> openWishes) {
		this.openWishes = openWishes;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public List<SalesLeadLine> getSalesLeadLines() {
		return salesLeadLines;
	}
	public void setSalesLeadLines(List<SalesLeadLine> salesLeadLines) {
		this.salesLeadLines = salesLeadLines;
	}
	public GaugeChartData getSatisfactionIndex() {
		return satisfactionIndex;
	}
	public void setSatisfactionIndex(GaugeChartData satisfactionIndex) {
		this.satisfactionIndex = satisfactionIndex;
	}
	
	
	
	
	
	
	
}
