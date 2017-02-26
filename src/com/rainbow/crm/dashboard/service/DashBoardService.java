package com.rainbow.crm.dashboard.service;

import java.util.Date;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.model.graphdata.BarChartData.Division;

public class DashBoardService  implements IDashBoardService{

	private SalesPeriod getSalesPeriodforUser(User associate, Date date,CRMContext context)
	{
		ISalesPeriodService salesPeriodService = (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		return salesPeriodService.getSalesPeriodforAssociate(associate.getUserId(), date);

	}

	@Override
	public BarChartData setSalesTargetData(User associate, Date date,
			CRMContext context) {
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Target vs Actual");
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		SalesPeriodAssociate salesPerAssociate = currentPeriod.getSalesPeriodAssociates().stream().findFirst().get();
		Double target  = salesPerAssociate.getLineTotal() ;
		ISalesService salesService =  (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		int soldQty = salesService.getSalesManSaleQuantity(
				salesPerAssociate.getUser(), currentPeriod.getFromDate(),
				currentPeriod.getToDate(), currentPeriod.getDivision());
		BarData tagetBarData = new BarData();
		
		BarChartData.Division targetDivis = barChartData.new Division();
		tagetBarData.setText("Target");
		tagetBarData.setLegend("Target");
		tagetBarData.setValue(target);
		targetDivis.addBarData(tagetBarData);
		
		BarData actualBarData = new BarData();
		actualBarData.setText("Actual");
		actualBarData.setLegend("Actual");
		actualBarData.setValue(soldQty);
		targetDivis.addBarData(actualBarData);
		
		targetDivis.setDivisionTitle("Sales Figures");
		barChartData.addDivision(targetDivis);


		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax( (int)((target>soldQty)?target:soldQty));
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		return barChartData;
	}

	@Override
	public LineChartData getSalesHistory(User associate, Date date,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PieChartData getPortfolioSplits(User associate, Date date,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
