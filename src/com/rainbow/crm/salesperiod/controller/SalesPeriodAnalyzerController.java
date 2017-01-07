package com.rainbow.crm.salesperiod.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAnalyzer;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesPeriodAnalyzerController  extends GeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		// TODO Auto-generated method stub
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
		SalesPeriodAnalyzer analyzer=  (SalesPeriodAnalyzer) object;
		int period = analyzer.getSalePeriod();
		if  (period > 0 ) {
			ISalesPeriodService service =(ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService") ;
			ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService") ;
			SalesPeriod salesPeriod = (SalesPeriod)service.getById(period) ;
			BarChartData barChartData = new BarChartData();
			Set<SalesPeriodLine> periodLines =  salesPeriod.getSalesPeriodLines() ;
			int minY = 0 , maxY =0;
			for  (SalesPeriodLine periodLine : periodLines) {
				BarData barData = new BarData();
				barData.setText(periodLine.getSku().getName());
				if (periodLine.getQty() > maxY ) {
					maxY= periodLine.getQty();
				}
				barData.setValue(periodLine.getQty());
				barData.setColor("green");
				barData.setTextColor("blue");
				BarChartData.Division divis =  barChartData.new Division();
				divis.addBarData(barData);
 				
 				
 				BarData actualSales = new BarData();
 				//actualSales.setText(periodLine.getItem().getName());
 				int soldQty = salesService.getItemSaleQuantity(periodLine.getSku(), salesPeriod.getFromDate(), salesPeriod.getToDate(), salesPeriod.getDivision()) ;
 				actualSales.setValue(soldQty);
 				actualSales.setColor("red");
				divis.addBarData(actualSales);
				
				barChartData.addDivision(divis);
			}
			BarChartData.Range range = barChartData.new Range();
			range.setyMax(maxY);
			range.setyMin(0);
			barChartData.setRange(range);
			analyzer.setSalesData(barChartData);
		}
		
		return new PageResult(); 
	}
	
	public Map <String, String > getItemClassTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ITEMCLASS_TYPE);
		return ans;
	}
	public Map<String, String >  getSalePeriods() {
		ISalesPeriodService service =  (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService") ;
		List<SalesPeriod> periods = (List<SalesPeriod>)service.listData(0, 1000, null, (CRMContext)getContext());
		Map<String, String> ans = new HashMap<String, String>();
		if(!Utils.isNullList(periods)) {
			for (SalesPeriod period : periods) {
				ans.put(String.valueOf(period.getId()),period.getPeriod());
			}
			return ans;
		}
		return null;
	}
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}

}
