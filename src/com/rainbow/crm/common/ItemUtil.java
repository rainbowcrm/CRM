package com.rainbow.crm.common;

import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;

public class ItemUtil {

	public static double getRetailPrice(Sku sku)
	{
		if(sku == null || sku.getItem() == null) return  0;
		String priceFrom = ConfigurationManager.getConfig(ConfigurationManager.FETCH_PRICESFROM, sku.getCompany().getId());
		if(CRMConstants.PRICE_SOURCES.SKU.equalsIgnoreCase(priceFrom))
			return sku.getRetailPrice();
		else
			return sku.getItem().getRetailPrice();	
	}
	
	public static double getRetailPrice(Item item)
	{
		if(item != null )
			return item.getRetailPrice();
		else
			return  0;
	}
	
	public static Product getProduct(CRMContext context,Product product)
	{
		IProductService prodService = (IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
		if (product == null) return null;
		if(product.getId() > 0 )
			product = (Product)prodService.getById(product.getId());
		else if (product.getBK() != null )
			product = (Product)prodService.getByBusinessKey(product, context);
		
		return product ;
		
		
			
	}
}
