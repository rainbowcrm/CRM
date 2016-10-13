package com.rainbow.crm.followup.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Followup",xmlTag="Followup")
public class Followup extends CRMBusinessModelObject{
   
	SalesLead lead;
	String conversation;
	FiniteValue confidenceLevel;
	FiniteValue communicationMode;
	Double offeredPrice;
	String salesAssociate ;
	Date nextFollwup;
	boolean finalFollowup;
	FiniteValue result;
	FiniteValue resultReason ;
	String comments;
	
	public SalesLead getLead() {
		return lead;
	}
	public void setLead(SalesLead lead) {
		this.lead = lead;
	}
	public String getConversation() {
		return conversation;
	}
	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	public FiniteValue getConfidenceLevel() {
		return confidenceLevel;
	}
	public void setConfidenceLevel(FiniteValue confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
	public FiniteValue getCommunicationMode() {
		return communicationMode;
	}
	public void setCommunicationMode(FiniteValue communicationMode) {
		this.communicationMode = communicationMode;
	}
	public Double getOfferedPrice() {
		return offeredPrice;
	}
	public void setOfferedPrice(Double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	public String getSalesAssociate() {
		return salesAssociate;
	}
	public void setSalesAssociate(String salesAssociate) {
		this.salesAssociate = salesAssociate;
	}
	public Date getNextFollwup() {
		return nextFollwup;
	}
	public void setNextFollwup(Date nextFollwup) {
		this.nextFollwup = nextFollwup;
	}
	public boolean isFinalFollowup() {
		return finalFollowup;
	}
	public void setFinalFollowup(boolean finalFollowup) {
		this.finalFollowup = finalFollowup;
	}
	public FiniteValue getResult() {
		return result;
	}
	public void setResult(FiniteValue result) {
		this.result = result;
	}
	public FiniteValue getResultReason() {
		return resultReason;
	}
	public void setResultReason(FiniteValue resultReason) {
		this.resultReason = resultReason;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

}
