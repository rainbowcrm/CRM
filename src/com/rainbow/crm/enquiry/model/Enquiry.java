package com.rainbow.crm.enquiry.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Enquiry",xmlTag="Enquiry")
public class Enquiry extends CRMBusinessModelObject{
	
	Division division;
	Territory territory;
	FiniteValue enquiryType;
	Contact contact;
	User salesAssociate;
	String enquiry;
	Date enqDate;
	Item nearestItem;
	Sku nearestSku;
	String comments;
	
	
	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getEnqDate() {
		return enqDate;
	}
	public void setEnqDate(Date enqDate) {
		this.enqDate = enqDate;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Territory getTerritory() {
		return territory;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setTerritory(Territory territory) {
		this.territory = territory;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public FiniteValue getEnquiryType() {
		return enquiryType;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setEnquiryType(FiniteValue enquiryType) {
		this.enquiryType = enquiryType;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Contact getContact() {
		return contact;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public User getSalesAssociate() {
		return salesAssociate;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setSalesAssociate(User salesAssociate) {
		this.salesAssociate = salesAssociate;
	}
	
	public String getEnquiry() {
		return enquiry;
	}
	public void setEnquiry(String enquiry) {
		this.enquiry = enquiry;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Item getNearestItem() {
		return nearestItem;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setNearestItem(Item nearestItem) {
		this.nearestItem = nearestItem;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Sku getNearestSku() {
		return nearestSku;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setNearestSku(Sku nearestSku) {
		this.nearestSku = nearestSku;
	}
	
	
	

}
