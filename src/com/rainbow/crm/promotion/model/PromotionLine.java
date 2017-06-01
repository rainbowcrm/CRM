package com.rainbow.crm.promotion.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;

public class PromotionLine extends CRMBusinessModelObject{
	
	FiniteValue masterPortFolioType ;
	String masterPortFolioKey;
	FiniteValue childPortFolioType;
	String childPortFolioKey; 
	
	Double requiredQty;
	Double promotedQty;
	Double requiredAmount;
	Double promotedDiscPercent;
	
	String comments;
	int lineNumber;
	
	Promotion promotion;
	
	public FiniteValue getMasterPortFolioType() {
		return masterPortFolioType;
	}
	public void setMasterPortFolioType(FiniteValue masterPortFolioType) {
		this.masterPortFolioType = masterPortFolioType;
	}
	public String getMasterPortFolioKey() {
		return masterPortFolioKey;
	}
	public void setMasterPortFolioKey(String masterPortFolioKey) {
		this.masterPortFolioKey = masterPortFolioKey;
	}
	public FiniteValue getChildPortFolioType() {
		return childPortFolioType;
	}
	public void setChildPortFolioType(FiniteValue childPortFolioType) {
		this.childPortFolioType = childPortFolioType;
	}
	public String getChildPortFolioKey() {
		return childPortFolioKey;
	}
	public void setChildPortFolioKey(String childPortFolioKey) {
		this.childPortFolioKey = childPortFolioKey;
	}
	public Double getRequiredQty() {
		return requiredQty;
	}
	public void setRequiredQty(Double requiredQty) {
		this.requiredQty = requiredQty;
	}
	public Double getPromotedQty() {
		return promotedQty;
	}
	public void setPromotedQty(Double promotedQty) {
		this.promotedQty = promotedQty;
	}
	public Double getRequiredAmount() {
		return requiredAmount;
	}
	public void setRequiredAmount(Double requiredAmount) {
		this.requiredAmount = requiredAmount;
	}
	public Double getPromotedDiscPercent() {
		return promotedDiscPercent;
	}
	public void setPromotedDiscPercent(Double promotedDiscPercent) {
		this.promotedDiscPercent = promotedDiscPercent;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	

}

