package com.rainbow.crm.salespitch.model;

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

@RadsPropertySet(jsonTag="Salespitch",xmlTag="Salespitch")
public class Salespitch extends CRMBusinessModelObject{
	
	Division division;
	Territory territory;
	FiniteValue salespitchType;
	Contact contact;
	User salesAssociate;
	String title;
	String date;
	Date pitchDate;
	Item item;
	String comments;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getPitchDate() {
		return pitchDate;
	}
	public void setPitchDate(Date enqDate) {
		this.pitchDate = enqDate;
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
	public FiniteValue getSalespitchType() {
		return salespitchType;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setSalespitchType(FiniteValue salespitchType) {
		this.salespitchType = salespitchType;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Item getItem() {
		return item;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setItem(Item nearestItem) {
		this.item = nearestItem;
	}

}
