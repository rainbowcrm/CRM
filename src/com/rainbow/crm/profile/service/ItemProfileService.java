package com.rainbow.crm.profile.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.feedback.service.IFeedBackService;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.profile.model.ItemProfile;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.wishlist.model.WishListLine;

public class ItemProfileService implements IItemProfileService{

	
	
	@Override
	public ItemProfile getItemProfile(Item item, CRMContext context) {
		IFeedBackService  service = (IFeedBackService) SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		Date fromDate = new java.util.Date((new java.util.Date().getTime() - 6L * 30L * 24L * 60L * 60L * 1000L));
		service.getLinesforItem(item,context,fromDate,new java.util.Date());
		return null;
	}

	public List<FeedBackLine> getCustomerFeedBacks(Item item, CRMContext context) {
		IFeedBackService  service = (IFeedBackService) SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		service.getLinesforItem(item,context,null,null);
		return null;
	}

	public List<SalesLine> getPastSales(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SalesLine> getPastReturns(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Inventory> getInventory(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Document> getDocument(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WishListLine> getWishList(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
