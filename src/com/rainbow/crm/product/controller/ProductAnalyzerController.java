package com.rainbow.crm.product.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductAnalyzer;
import com.rainbow.crm.product.service.IProductService;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.model.graphdata.PieSliceData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ProductAnalyzerController  extends GeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}

	@Override
	public IRadsContext generateContext(String authToken) {
		// TODO Auto-generated method stub
		return LoginSQLs.loggedInUser(authToken);
	}

	@Override
	public PageResult read(ModelObject object) {
		String [] colors = { "Red","Blue" ,"Green" , "Violet" , "Indigo" , "Majenta" ,"Brown" ,"Yellow" , "Orange", 
				"Salmon","Gray","SandyBrown","Ivory","CadetBlue","OrangeRed","SeaGreen"} ;
		ProductAnalyzer analyzer = (ProductAnalyzer) object;
		PieChartData  pieTopChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.TOP_END,colors);
		analyzer.setTopSalesData(pieTopChartData);
		
		PieChartData  pieupMedChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.UPPER_MEDIUM,colors);
		analyzer.setUpMedSalesData(pieupMedChartData);
		
		PieChartData  pielowMedChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.LOWER_MEDIUM,colors);
		analyzer.setLowMedSalesData(pielowMedChartData);
		
		PieChartData  pieeconChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.ECONOMIC,colors);
		analyzer.setEconSalesData(pieeconChartData);
		
		return new PageResult();
	}
	
	private PieChartData readByItemCass  (ProductAnalyzer analyzer , String itemClass, String []colors) {
		int index = 0;
		PieChartData pieChartData = new PieChartData();
		Date fromDate = analyzer.getFromDate() ;
		Date toDate = analyzer.getToDate() ;
		Product product = analyzer.getProduct();
		IProductService service = (IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
		product =(Product) service.getByBusinessKey(product,(CRMContext) getContext());
		ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
		Map map = salesService.getItemSoldQtyByProduct(product, fromDate, toDate,null,itemClass) ;
		Set keys = map.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			PieSliceData pieSliceData = new PieSliceData();
			Integer itemId = (Integer) it.next() ;
			Item item = (Item)itemService.getById(itemId);
			Double qty = (Double) map.get(itemId);
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item.getName() + "-" +  qty);
			pieSliceData.setColor(colors[index ++ ]);
			pieChartData.addPieSlice(pieSliceData);
		}
		FiniteValue desc = GeneralSQLs.getFiniteValue(itemClass);
		pieChartData.setFooterNote(product.getName() + "-" + desc.getDescription());
		return pieChartData;
	}
	
	
	

}
