package com.rainbow.crm.dashboard.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
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
import com.techtrade.rads.framework.model.graphdata.PieSliceData;

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
		barChartData.setSubTitle(" ");
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
		tagetBarData.setColor(CommonUtil.getGraphColors()[0]);
		targetDivis.addBarData(tagetBarData);
		
		BarData actualBarData = new BarData();
		actualBarData.setText("Actual");
		actualBarData.setLegend("Actual");
		actualBarData.setValue(soldQty);
		actualBarData.setColor(CommonUtil.getGraphColors()[1]);
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
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		Map <String , Double > results = DashBoardSQLs.getItemWiseSale(currentPeriod.getDivision().getId(), associate.getUserId(), new java.sql.Date( currentPeriod.getFromDate().getTime()),
				new java.sql.Date( currentPeriod.getToDate().getTime())) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Item");
		pieChartData.setTitle("Sale by Item");
		return pieChartData;
				
	}

	
	
}
