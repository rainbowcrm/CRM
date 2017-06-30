package com.rainbow.crm.promotion.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;

public class PromotionEngine {

	Map<PromotionLine,Integer> unusedPromotions =  new HashMap<PromotionLine,Integer> ();
	
	Map<PromotionLine,Integer> unconsumedIncentives =  new HashMap<PromotionLine,Integer> ();
	
	
	public void populatePromotion(Sales sales)
	{
		IPromotionService promotionService = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		for (SalesLine salesLine : sales.getSalesLines() ) {
			 PromotionLine promotionLine   =  promotionService.getPromotionforSKU(salesLine.getSku(),sales.getSalesDate());
			 if (promotionLine  != null) {
				 if (unusedPromotions.containsKey(promotionLine)) {
					 int qty = unusedPromotions.get(promotionLine);
					 qty += salesLine.getQty();
					 unusedPromotions.put(promotionLine, qty);
				 }else {
					 unusedPromotions.put(promotionLine, salesLine.getQty());
				 }
				 continue ;
			 }
			 Promotion promotion = promotionService.getAllItemPromotion(sales.getSalesDate(), sales.getDivision());
			 if (promotion  != null) {
				 
				 continue ;
			 }
			 
			 PromotionLine promotedLine   =  promotionService.isIncentivizedSku(salesLine.getSku(),sales.getSalesDate());
			 if (promotedLine  != null) {
				 if (unconsumedIncentives.containsKey(promotedLine)) {
					 int qty = unconsumedIncentives.get(promotedLine);
					 qty += salesLine.getQty();
					 unconsumedIncentives.put(promotedLine, qty);
				 }else {
					 unconsumedIncentives.put(promotedLine, salesLine.getQty());
				 }
				 continue ;
			 }
		}
		
		Iterator it = unusedPromotions.keySet().iterator();
		
		while( it.hasNext()) {
			PromotionLine promotedLine = (PromotionLine)it.next() ;
			if(promotedLine.getPromotion().getPromoType().equals(CRMConstants.PROMOTYPE.BUNDLING))
			{
				
			}
			if(promotedLine.getPromotion().getPromoType().equals(CRMConstants.PROMOTYPE.CROSSSELL))
			{
				if (unconsumedIncentives.containsKey(promotedLine)) {
					
				}
				
			}
			if(promotedLine.getPromotion().getPromoType().equals(CRMConstants.PROMOTYPE.UPSELLING))
			{
				if (unconsumedIncentives.containsKey(promotedLine)) {
					
				}
				
			}
			
			
		}
		
		
	}
	
	
	private void applyBundling(PromotionLine promotedLine ) 
	{
		if (unconsumedIncentives.containsKey(promotedLine)) 
		{
			Integer qty = unconsumedIncentives.get(promotedLine);
			//Integer deducedQty = qty - Integer.valueOf(promotedLine.getRequiredQty()) ;
			
			
		}
	}
	
	private void applyCrossSelling() 
	{
		
	}
	
	private void applyUpSelling() 
	{
		
	}
	
}
