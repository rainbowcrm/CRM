package com.rainbow.crm.common;

import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;

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
}
