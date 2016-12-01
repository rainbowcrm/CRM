package com.rainbow.crm.sales.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.rainbow.crm.sales.model.SalesTrend;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartEntryData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesTrendController  extends GeneralController{

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
		return LoginSQLs.loggedInUser(authToken);
	}

	@Override
	public PageResult read(ModelObject object) {
		LineChartData lineChartData = new LineChartData();
		SalesTrend trend = (SalesTrend) object;
		int period = trend.getNoItervals() ;
		Date fromDate = trend.getFromDate();
		Date toDate = trend.getToDate() ;
		long diff =  (toDate.getTime()  - fromDate.getTime())/period  ;
		Product selectedProduct =  trend.getProduct();
		IProductService iProdService = (IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService") ;
		selectedProduct = iProdService.getByName(((CRMContext)getContext()).getLoggedinCompany(), selectedProduct.getName());
		IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
		ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		List<Item> items = itemService.getAllByProduct(((CRMContext)getContext()).getLoggedinCompany(), selectedProduct.getId());
		Date periodFrom  = new Date(fromDate.getTime()) ;
		Date periodTo = new Date(periodFrom.getTime() + diff) ;
		int maxValue = 0;
		Map <Integer,LineChartEntryData> itemMap = new HashMap<Integer,LineChartEntryData>() ;
		for (int i = 0 ; i <  period ; i ++) 
		{
			try {
				lineChartData.addInterval(Utils.dateToString(fromDate, "dd-mm-yyyy"));
			Map mapSales = salesService.getItemSoldQtyByProduct(selectedProduct, periodFrom, periodTo, null, null);
			Iterator it = mapSales.keySet().iterator();
			while(it.hasNext()) {
				Integer itemId = (Integer)it.next() ;
				Double qty = (Double) mapSales.get(itemId);
				if (qty > maxValue)
					maxValue = new Double(qty).intValue(); 
				LineChartEntryData lineChartEntryData ;
				if (itemMap.get(itemId) == null) {
					lineChartEntryData = new LineChartEntryData();
					Item item = (Item)itemService.getById(itemId);
					lineChartEntryData.setText(item.getName());
				}else {
					lineChartEntryData = (LineChartEntryData)itemMap.get(itemId) ;
				}
				lineChartEntryData.addToValueMap(Utils.dateToString(fromDate, "dd-mm-yyyy"), qty);
				itemMap.put(itemId, lineChartEntryData);
			}
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
			periodFrom.setTime(periodTo.getTime() +  (24*60*60*1000));
			periodTo.setTime(periodFrom.getTime() + diff);
					
		}
		LineChartData.Range range = lineChartData.new Range();
		range.setyMax(maxValue);
		lineChartData.setRange(range);
		Iterator it = itemMap.keySet().iterator();
		while(it.hasNext()) {
			LineChartEntryData entry = itemMap.get(it.next());
			lineChartData.addEntry(entry);
			
		}
		trend.setChartData(lineChartData);
		return super.read(object);
	}

}
