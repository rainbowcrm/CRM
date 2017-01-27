package com.rainbow.crm.lookups;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.database.LoginSQLs;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;

public class LookupSalesPortfolioKeys implements ILookupService{

	String keyType;
	
	@Override
	public List<Object> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords , String lookupParam) {
		ILookupService lookup ;
		if(CRMConstants.SALESPFTYPE.CATEGORY.equals(lookupParam)) {
			lookup = new LookupCategories();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam) ;
		}
		if(CRMConstants.SALESPFTYPE.BRAND.equals(lookupParam)) {
			lookup = new LookupBrands();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam) ;
		}
		if(CRMConstants.SALESPFTYPE.PRODUCT.equals(lookupParam)) {
			lookup = new LookupProducts();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam) ;
			
		}if(CRMConstants.SALESPFTYPE.ITEM.equals(lookupParam)) {
			lookup = new LookupItems();
			return lookup.lookupData(ctx, searchString, from, noRecords, lookupParam) ;
		}
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request.getSession().getId());
	}
	
	

}
