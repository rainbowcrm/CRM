package com.rainbow.crm.abstratcs.model;

import com.rainbow.crm.item.model.Item;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public  class CRMItemLine extends CRMBusinessModelObject {
	
	Item item;
	int qty;
	boolean voided;
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Item getItem() {
		return item;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setItem(Item item) {
		this.item = item;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public boolean getVoided() {
		return voided;
	}
	public void setVoided(boolean isVoided) {
		this.voided = isVoided;
	}

}
