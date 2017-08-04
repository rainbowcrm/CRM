package com.rainbow.crm.profile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.service.IDocumentService;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.feedback.service.IFeedBackService;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.profile.model.CustomerProfile;
import com.rainbow.crm.profile.model.ItemProfile;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.rainbow.crm.wishlist.service.IWishListService;

public class ItemProfileService implements IItemProfileService{

	 private void loadImage(ItemProfile itemProfile,CRMContext context )
	 {
		 try {
		 	String imgPath = "./resources/images/not-available.png";
			String filePath = CRMAppConfig.INSTANCE.getProperty("doc_server");
			String code = context.getLoggedinCompanyCode();
			ItemImage dbRecord1 = ItemImageSQL.getItemImage(itemProfile.getItem().getId(), 'a');
			if (dbRecord1 != null ) {
				imgPath = filePath + "\\" +  code  + "\\itemimages\\" +  dbRecord1.getFileName();
			}
			itemProfile.setItemImage(imgPath);
		 }catch(Exception ex)
		 {
			 Logwriter.INSTANCE.error(ex);
		 }
	 }
	
	@Override
	public CustomerProfile getCustomerProfile(Customer customer,
			CRMContext context) {
		CustomerProfile custProfile = new CustomerProfile();
		String profDataHist = ConfigurationManager.getConfig(ConfigurationManager.PROF_DATAHISTORY, context);
		Date fromDate = CommonUtil.getRelativeDate(new FiniteValue(profDataHist));
		Date toDate = new java.util.Date();
		
		IFeedBackService  service = (IFeedBackService) SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		List<FeedBackLine> feedBackLines =service.getLinesforCustomer(customer, context, fromDate, toDate);
		custProfile.setFeedBackLines(feedBackLines);
		
		IDocumentService docService = (IDocumentService) SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		List<Document> docLines = docService.findAllByCustomer(customer);
		custProfile.setDocuments(docLines);
		
		IWishListService wishListService = (IWishListService) SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		List<WishListLine> wishesList = wishListService.getWishesforCustomer(customer, context, fromDate, new java.util.Date()) ;
		custProfile.setOpenWishes(wishesList);
		
		ISalesService  salesService =(ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		List<SalesLine> sales = salesService.getSalesForCustomer(customer, context, false, fromDate, new java.util.Date());
		custProfile.setPastSales(sales);
		
		ISalesLeadService  salesLeadService =(ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		List<SalesLeadLine> salesLeadLines =  salesLeadService.getSalesLeadLinesforCustomer(customer, context, fromDate, toDate);
		custProfile.setSalesLeadLines(salesLeadLines);
		
		return custProfile;
		
	}



	@Override
	public ItemProfile getItemProfile(Item item, CRMContext context) {
		ItemProfile itemProfile = new ItemProfile();
		itemProfile.setItem(item);
		
		loadImage(itemProfile, context);
		
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
		
		List<Inventory> inventoryList = new ArrayList<Inventory> ();
		
		if ( skuList != null ) {
			skuList.forEach(sku ->  {   
				List<Inventory> skuInventory = inventoryService.getByItem(sku);
				inventoryList.addAll(skuInventory);
				
				
			});
		}
		
		itemProfile.setInventory(inventoryList);
		IWishListService wishListService = (IWishListService) SpringObjectFactory.INSTANCE.getInstance("IWishListService");
		List<WishListLine> wishesList = wishListService.getWishesforItem(item, context, fromDate, new java.util.Date()) ;
		itemProfile.setWishList(wishesList);
		
		ISalesService  salesService =(ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		List<SalesLine> sales = salesService.getSalesForItem(item, context, false, fromDate, new java.util.Date());
		itemProfile.setPastSales(sales);
				
		return itemProfile;
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
