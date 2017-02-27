package com.rainbow.crm.dashboard.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartEntryData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.model.graphdata.BarChartData.Division;
import com.techtrade.rads.framework.model.graphdata.LineChartData.Range;
import com.techtrade.rads.framework.model.graphdata.PieSliceData;
import com.techtrade.rads.framework.utils.Utils;

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
		
		LineChartData lineChartData = new LineChartData();
		long points = 3;
		int maxValue = 0;
	try { 
		LineChartEntryData lineChartEntryData  = new LineChartEntryData();
		lineChartData.setBorderColor(CommonUtil.getGraphColors()[1] );
		lineChartData.setTitle("Sales Progression");
		lineChartData.setSubTitle("Sales History");
		lineChartEntryData.setColor(CommonUtil.getGraphColors()[1] );
		for (long i = points;  i >0 ; i--) {
			Date startDate = new Date(date.getTime() -  (7 * i * 24l * 3600l * 1000l  ));
			Date endDate = new Date(date.getTime() -  (7 * (i-1) * 24l * 3600l * 1000l  ));
			Double saleQty = DashBoardSQLs.getPeriodTotalSale(associate.getUserId(), new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
			if (i == points){
				lineChartData.setStartingPoint(Utils.dateToString(startDate, "dd-MM-yyyy"));
			}
			lineChartData.addInterval(Utils.dateToString(endDate, "dd-MM-yyyy"));
			lineChartEntryData.addToValueMap(Utils.dateToString(endDate, "dd-MM-yyyy"), saleQty);
			if (saleQty > maxValue )
				maxValue = new Double(saleQty).intValue();
				 
					
		}
		lineChartData.addEntry(lineChartEntryData);
		LineChartData.Range range = lineChartData.new Range();
		range.setyMax(maxValue);
		lineChartData.setRange(range);
	}catch(Exception ex) {
		Logwriter.INSTANCE.error(ex);
	}
		
		return lineChartData;
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
