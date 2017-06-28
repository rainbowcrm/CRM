package com.rainbow.crm.item.model;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.inventory.model.Inventory;

public class SkuComplete  extends Sku{

	private String image1URL;
	private String image2URL;
	private String image3URL;
	
	List<Inventory> inventory;
	List<Document> documents;
	
	public String getImage1URL() {
		return image1URL;
	}
	public void setImage1URL(String image1url) {
		image1URL = image1url;
	}
	public String getImage2URL() {
		return image2URL;
	}
	public void setImage2URL(String image2url) {
		image2URL = image2url;
	}
	public String getImage3URL() {
		return image3URL;
	}
	public void setImage3URL(String image3url) {
		image3URL = image3url;
	}
	public SkuComplete() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SkuComplete(Sku sku) {
		 super();
		 setId(sku.getId());
		 setName(sku.getName());
		 setProduct(sku.getProduct());
		 setItem(sku.getItem());
		 setItemClass(sku.getItemClass());
		 setRetailPrice(sku.getRetailPrice());
		 setCode(sku.getCode());
		 setBreakEvenPrice(sku.getBreakEvenPrice());
		 setPurchasePrice(sku.getPurchasePrice());
		 setPromotionPrice(sku.getPromotionPrice());
		 setCompany(sku.getCompany());
		 setDescription(sku.getDescription());
		 setSpecification(sku.getSpecification());
		 setManufacturer(sku.getManufacturer());
		 setMaxDiscount(sku.getMaxDiscount());
		 setMaxPrice(sku.getMaxPrice());
		 setOnPromotion(sku.isOnPromotion());
		 setWholeSalePrice(sku.getWholeSalePrice());
		 setUom(sku.getUom());
		 setUomId(sku.getUomId());
	}
	public List<Inventory> getInventory() {
		return inventory;
	}
	public void setInventory(List<Inventory> inventory) {
		this.inventory = inventory;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	
	
	
}
