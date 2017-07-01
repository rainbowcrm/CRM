package com.rainbow.crm.feedback.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class FeedBackLine extends CRMBusinessModelObject{
	
	int lineNumber;
	FiniteValue feedBackObjectType;
	String feedBackObject ;
	int rating ;
	ReasonCode reasonCode;
	String comments;
	Boolean deleted;
	
	FeedBack feedBackDoc;


	public FeedBackLine() {
	
	}
	@RadsPropertySet(isBK=true)
	public int getLineNumber() {
		return lineNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public FeedBack getFeedBackDoc() {
		return feedBackDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setFeedBackDoc(FeedBack feedBackDoc) {
		this.feedBackDoc = feedBackDoc;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public FiniteValue getFeedBackObjectType() {
		return feedBackObjectType;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setFeedBackObjectType(FiniteValue feedBackObjectType) {
		this.feedBackObjectType = feedBackObjectType;
	}
	public String getFeedBackObject() {
		return feedBackObject;
	}
	public void setFeedBackObject(String feedBackObject) {
		this.feedBackObject = feedBackObject;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public ReasonCode getReasonCode() {
		return reasonCode;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setReasonCode(ReasonCode reasonCode) {
		this.reasonCode = reasonCode;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	

}
