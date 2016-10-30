package com.rainbow.crm.abstratcs.model;

import java.util.Arrays;
import java.util.Base64;

import com.rainbow.crm.item.model.Item;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public  class CRMItemLine extends CRMBusinessModelObject {
	
	Item item;
	int qty;
	boolean voided;
	
	private byte [] imgBytes1 ;
	private byte [] imgBytes2 ;
	private byte [] imgBytes3 ;
	
	
	
	
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
	public byte[] getImgBytes1() {
		return imgBytes1;
	}
	public String getImgBytes1Str() {
		Base64.Encoder base64 = Base64.getEncoder();
		return base64.encodeToString(imgBytes1);
	}
	public void setImgBytes1(byte[] imgBytes1) {
		this.imgBytes1 = imgBytes1;
	}
	public String getImgBytes2Str() {
		Base64.Encoder base64 = Base64.getEncoder();
		return base64.encodeToString(imgBytes2);
	}
	public byte[] getImgBytes2() {
		return imgBytes2;
	}
	public void setImgBytes2(byte[] imgBytes2) {
		this.imgBytes2 = imgBytes2;
	}
	public byte[] getImgBytes3() {
		return imgBytes3;
	}
	public String getImgBytes3Str() {
		Base64.Encoder base64 = Base64.getEncoder();
		return base64.encodeToString(imgBytes3);
	}
	public void setImgBytes3(byte[] imgBytes3) {
		this.imgBytes3 = imgBytes3;
	}
	
	
	

}
