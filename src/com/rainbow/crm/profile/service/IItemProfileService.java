package com.rainbow.crm.profile.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.wishlist.model.WishListLine;

public interface IItemProfileService {

	public List<FeedBackLine> getCustomerFeedBacks(Item item , CRMContext context );
	public List<SalesLine> getPastSales(Item item , CRMContext context );
	public List<SalesLine> getPastReturns(Item item , CRMContext context );
	public List<Inventory> getInventory(Item item , CRMContext context );
	public List<Document> getDocument(Item item , CRMContext context );
	public List<WishListLine> getWishList(Item item , CRMContext context );
	
	
}
