package com.rainbow.crm.promotion.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;

public class CrossSellingEngine {
	
Map<PromotionLine,List<SalesLine>> unusedPromotions =  new HashMap<PromotionLine,List<SalesLine>> ();
	
	Map<PromotionLine,List<SalesLine>> unconsumedIncentives =  new HashMap<PromotionLine,List<SalesLine>> ();
	
	
	public void populatePromotion(Sales sales)
	{
		IPromotionService promotionService = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		for (SalesLine salesLine : sales.getSalesLines() ) {
			 PromotionLine promotionLine   =  promotionService.getPromotionforSKU(salesLine.getSku(),sales.getSalesDate());
			 if (promotionLine  != null) {
				 if (unusedPromotions.containsKey(promotionLine)) {
					 List<SalesLine> promotedSaleslines = unusedPromotions.get(promotionLine);
					 promotedSaleslines.add(salesLine);
					 unusedPromotions.put(promotionLine, promotedSaleslines);
				 }else {
					 List<SalesLine> promotedSalesLines = new ArrayList<SalesLine>();
					 promotedSalesLines.add(salesLine);
					 unusedPromotions.put(promotionLine, promotedSalesLines);
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
					 List<SalesLine> incentiveSalesLines = unconsumedIncentives.get(promotedLine);
					 incentiveSalesLines.add(salesLine);
					 unconsumedIncentives.put(promotedLine, incentiveSalesLines);
				 }else {
					 List<SalesLine> incentiveSalesLines = new ArrayList<SalesLine>();
					 incentiveSalesLines.add(salesLine);
					 unconsumedIncentives.put(promotedLine, incentiveSalesLines);
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


}
