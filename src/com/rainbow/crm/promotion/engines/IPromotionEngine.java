package com.rainbow.crm.promotion.engines;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IPromotionEngine {

	public RadsError searchForUnConsumedPromotions(Sales sales, CRMContext context);
	
	public void applyPromotions(Sales sales, CRMContext context);
}
