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

public class PromotionEngine {

	Map<PromotionLine,List<SalesLine>> unusedPromotions =  new HashMap<PromotionLine,List<SalesLine>> ();
	
	Map<PromotionLine,List<SalesLine>> unconsumedIncentives =  new HashMap<PromotionLine,List<SalesLine>> ();
	
	
	public void populatePromotion(Sales sales)
	{
		
		
		
	}
	
	
	
	
}
