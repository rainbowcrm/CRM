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
	Date followupDate ;
	FiniteValue confidenceLevel;
	
	FiniteValue communicationMode;
	Double offeredPrice;
	String salesAssociate ;
	Date nextFollwup;
	boolean finalFollowup;
	FiniteValue result;
	FiniteValue resultReason ;
	String comments;
	boolean alerted; 
	
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public SalesLead getLead() {
		return lead;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setLead(SalesLead lead) {
		this.lead = lead;
	}
	public String getConversation() {
		return conversation;
	}
	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public FiniteValue getConfidenceLevel() {
		return confidenceLevel;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public void setConfidenceLevel(FiniteValue confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public FiniteValue getCommunicationMode() {
		return communicationMode;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
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
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public FiniteValue getResult() {
		return result;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public void setResult(FiniteValue result) {
		this.result = result;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public FiniteValue getResultReason() {
		return resultReason;
	}
	@RadsPropertySet(usePKForJSON=true,usePKForMap=true,usePKForXML=true)
	public void setResultReason(FiniteValue resultReason) {
		this.resultReason = resultReason;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public boolean isAlerted() {
		return alerted;
	}
	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}
	public Date getFollowupDate() {
		return followupDate;
	}
	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
	}
	

}
