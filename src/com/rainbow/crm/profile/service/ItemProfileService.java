package com.rainbow.crm.profile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.service.IDocumentService;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.feedback.service.IFeedBackService;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.profile.model.ItemProfile;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.rainbow.crm.wishlist.service.IWishListService;

public class ItemProfileService implements IItemProfileService{

	
	
	@Override
	public ItemProfile getItemProfile(Item item, CRMContext context) {
		ItemProfile itemProfile = new ItemProfile();
		itemProfile.setItem(item);
		String profDataHist = ConfigurationManager.getConfig(ConfigurationManager.PROF_DATAHISTORY, context);
		Date fromDate = CommonUtil.getRelativeDate(new FiniteValue(profDataHist));
		
		IFeedBackService  service = (IFeedBackService) SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		List<FeedBackLine> feedBackLines = service.getLinesforItem(item,context,fromDate,new java.util.Date());
		itemProfile.setCustomerFeedBacks(feedBackLines);

		IDocumentService docService = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		List<Document> docLines = docService.findAllByItem(item);
		itemProfile.setDocuments(docLines);
		
		ISkuService skuService = (ISkuService) SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		List<Sku> skuList = skuService.getAllByItem(context.getLoggedinCompany(), item.getId());
		
		IInventoryService inventoryService = (IInventoryService) SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
		IWishListService wishListService = (IWishListService) SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		List<Inventory> inventoryList = new ArrayList<Inventory> ();
		List<WishListLine> wishesList = new ArrayList<WishListLine> ();
		if ( skuList != null ) {
			skuList.forEach(sku ->  {   
				List<Inventory> skuInventory = inventoryService.getByItem(sku);
				inventoryList.addAll(skuInventory);
				
				List<WishListLine> skuWishes= wishListService.getWishesforSKU(sku, context, fromDate, new java.util.Date()) ;
				wishesList.addAll(skuWishes);
			});
		}
		
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
