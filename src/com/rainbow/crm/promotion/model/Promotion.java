package com.rainbow.crm.promotion.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Promotion",xmlTag="Promotion")
public class Promotion extends CRMBusinessModelObject{
   
	String name ;
	Division division ;
	FiniteValue promoType;
	CustCategory custCategory ;
	Date startDt;
	Date endDt;
	
	FiniteValue masterPortFolioType ;
	String masterPortFolioKey;
	FiniteValue childPortFolioType;
	String childPortFolioKey; 
	
	Double requiredQty;
	Double promotedQty;
	Double requiredAmount;
	Double promotedDiscPercent;
	
	Boolean isActive;
	String comments;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public FiniteValue getPromoType() {
		return promoType;
	}
	public void setPromoType(FiniteValue promoType) {
		this.promoType = promoType;
	}
	public CustCategory getCustCategory() {
		return custCategory;
	}
	public void setCustCategory(CustCategory custCategory) {
		this.custCategory = custCategory;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	

	

}
