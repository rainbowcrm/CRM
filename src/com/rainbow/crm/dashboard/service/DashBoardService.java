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

	private SalesPeriod getActiveSalesPeriodforManager(User associate, Date date,CRMContext context)
	{
		ISalesPeriodService salesPeriodService = (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		return salesPeriodService.getActiveSalesPeriodforDivision(associate.getDivision().getId(), date);

	}
	
	
	private SalesPeriodAssociate getAssociatePeriod(SalesPeriod currentPeriod, User associate)
	{
		for (SalesPeriodAssociate selectedAssociate  : currentPeriod.getSalesPeriodAssociates()) {
			if (selectedAssociate.getUser().getUserId().equals(associate.getUserId())) {
				return selectedAssociate;
			}
		}
		
		return null;
		
	}
	
	
	@Override
	public BarChartData setSalesTargetData(User associate, Date date,
			CRMContext context) {
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Target vs Actual");
		barChartData.setSubTitle(" ");
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		if(currentPeriod == null)
			return null;
		SalesPeriodAssociate salesPerAssociate = getAssociatePeriod(currentPeriod,associate);
		Double target  = salesPerAssociate.getLineTotal() ;
		ISalesService salesService =  (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		int soldQty = salesService.getSalesManSaleQuantity(
				associate, currentPeriod.getFromDate(),
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
		
		targetDivis.setDivisionTitle("My Sales Figures");
		barChartData.addDivision(targetDivis);

		int totalSoldQty = salesService.getTotalSaleQuantity(currentPeriod.getFromDate(),
				currentPeriod.getToDate(), currentPeriod.getDivision());
		
		int noAssociates  =getNoAssociates(associate, date, context) ;
		
		BarChartData.Division avgDataDivis = barChartData.new Division();
		BarData tagetAvgBarData = new BarData();
		tagetAvgBarData.setText("Target");
		tagetAvgBarData.setLegend("Target");
		tagetAvgBarData.setValue(currentPeriod.getTotalTarget()/noAssociates);
		tagetAvgBarData.setColor(CommonUtil.getGraphColors()[0]);
		avgDataDivis.addBarData(tagetAvgBarData);
		
		BarData actualavgBarData = new BarData();
		actualavgBarData.setText("Actual");
		actualavgBarData.setLegend("Actual");
		actualavgBarData.setValue(totalSoldQty/noAssociates);
		actualavgBarData.setColor(CommonUtil.getGraphColors()[1]);
		avgDataDivis.addBarData(actualavgBarData);
		
		avgDataDivis.setDivisionTitle("Avg Sales Figures");
		barChartData.addDivision(avgDataDivis);
		
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
		lineChartEntryData.setText("My Sale");
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
		
		LineChartEntryData lineChartavgEntry  = new LineChartEntryData();
		lineChartavgEntry.setColor(CommonUtil.getGraphColors()[4] );
		lineChartavgEntry.setText("Avg Division Sale");
		for (long i = points;  i >0 ; i--) {
			Date startDate = new Date(date.getTime() -  (7 * i * 24l * 3600l * 1000l  ));
			Date endDate = new Date(date.getTime() -  (7 * (i-1) * 24l * 3600l * 1000l  ));
			SalesPeriod currentPeriod = getSalesPeriodforUser(associate, endDate, context);
			if (currentPeriod == null)
				return null;
			Double saleQty = DashBoardSQLs.getSaleAllMade(currentPeriod.getDivision().getId(), new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
			int noAssociates  =getNoAssociates(associate, endDate, context) ;
			lineChartavgEntry.addToValueMap(Utils.dateToString(endDate, "dd-MM-yyyy"), saleQty/noAssociates);
			if (saleQty > maxValue )
				maxValue = new Double(saleQty).intValue();
					
		}
		lineChartData.addEntry(lineChartavgEntry);
		
		LineChartData.Range range = lineChartData.new Range();
		range.setyMax(maxValue);
		lineChartData.setRange(range);
	}catch(Exception ex) {
		Logwriter.INSTANCE.error(ex);
	}
		
		return lineChartData;
	}
	
	private int getNoAssociates (User associate, Date endDate, CRMContext context) 
	{
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, endDate, context);
		ISalesPeriodService service = (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		currentPeriod =(SalesPeriod) service.getById(currentPeriod.getPK());
		int noAssociates = 1;
		if (!Utils.isNullSet(currentPeriod.getSalesPeriodAssociates()))
			noAssociates = currentPeriod.getSalesPeriodAssociates().size();
		return noAssociates; 
	}
	
	

	

	@Override
	public PieChartData getProductwiseSales(User manager, Date date,
			CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
		if(currentPeriod == null) return null;
		Map <String , Double > results = DashBoardSQLs.getProductWiseSale(currentPeriod.getDivision().getId(),  new java.sql.Date( currentPeriod.getFromDate().getTime()),
				new java.sql.Date( currentPeriod.getToDate().getTime())) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Product");
		pieChartData.setTitle("Sale by Product");
		return pieChartData;

	}
	
		

	@Override
	public PieChartData getItemwiseSales(User manager, Date date,
			CRMContext context) {
		 PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
		if(currentPeriod == null) return null;
		Map <String , Double > results = DashBoardSQLs.getItemWiseSale(currentPeriod.getDivision().getId(),  new java.sql.Date( currentPeriod.getFromDate().getTime()),
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

	
	@Override
	public PieChartData getBrandwiseSales(User manager, Date date,
			CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
		if(currentPeriod == null) return null;
		Map <String , Double > results = DashBoardSQLs.getBrandWiseSale(currentPeriod.getDivision().getId(),  new java.sql.Date( currentPeriod.getFromDate().getTime()),
				new java.sql.Date( currentPeriod.getToDate().getTime())) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Brand");
		pieChartData.setTitle("Sale by Brand");
		return pieChartData;
	}
	
	@Override
	public PieChartData getCategorywiseSales(User manager, Date date,
			CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
		if(currentPeriod == null) return null;
		Map <String , Double > results = DashBoardSQLs.getCategoryWiseSale(currentPeriod.getDivision().getId(),  new java.sql.Date( currentPeriod.getFromDate().getTime()),
				new java.sql.Date( currentPeriod.getToDate().getTime())) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Category");
		pieChartData.setTitle("Sale by Category");
		return pieChartData;
	}

	
	
	@Override
	public PieChartData getLeadSplits(User associate, Date date,
			CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		if(currentPeriod == null) return null;
		Map <String , Double > results = DashBoardSQLs.getStatusWiseSaleLeads(currentPeriod.getDivision().getId(), associate.getUserId(), new java.sql.Date( currentPeriod.getFromDate().getTime()),
				new java.sql.Date( currentPeriod.getToDate().getTime())) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (status, count) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(count);
			pieSliceData.setText(status);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sales Lead by Status");
		pieChartData.setTitle("Sales Lead by Status");
		return pieChartData;
	}

	@Override
	public PieChartData getPortfolioSplits(User associate, Date date,
			CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		if(currentPeriod == null) return null;
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

	
	
	@Override
	public PieChartData getTerritorySplits(User manager, Date date,
			CRMContext context) {
		SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
		if (currentPeriod == null )
			return null;
		Map<String , Double> splits = DashBoardSQLs.getSaleMadeByTerritory(currentPeriod.getDivision().getId(), 
				new java.sql.Date(currentPeriod.getFromDate().getTime()), new java.sql.Date(currentPeriod.getToDate().getTime()));
		PieChartData pieChartData = new PieChartData();
		AtomicInteger index = new AtomicInteger(0);
		splits.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Territory");
		pieChartData.setTitle("Sale by Territory");
		return pieChartData;

	}

	@Override
	public PieChartData getAssociateSplits(User manager, Date date,
			CRMContext context) {
		SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
		if (currentPeriod == null )
			return null;
		Map<String , Double> splits = DashBoardSQLs.getSaleMadeByAssociate(currentPeriod.getDivision().getId(), 
				new java.sql.Date(currentPeriod.getFromDate().getTime()), new java.sql.Date(currentPeriod.getToDate().getTime()));
		PieChartData pieChartData = new PieChartData();
		AtomicInteger index = new AtomicInteger(0);
		splits.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Associate");
		pieChartData.setTitle("Sale by Associate");
		return pieChartData;
	}

	private BarChartData makeBarChartData (SalesPeriod currentPeriod,int totalSoldQty,String divisionTitle, String title )
	{
		BarChartData barChartData = new BarChartData();
		BarChartData.Division avgDataDivis = barChartData.new Division();
		BarData tagetAvgBarData = new BarData();
		tagetAvgBarData.setText("Target");
		tagetAvgBarData.setLegend("Target");
		tagetAvgBarData.setValue(currentPeriod.getTotalTarget());
		tagetAvgBarData.setColor(CommonUtil.getGraphColors()[0]);
		avgDataDivis.addBarData(tagetAvgBarData);
		
		BarData actualavgBarData = new BarData();
		actualavgBarData.setText("Actual");
		actualavgBarData.setLegend("Actual");
		actualavgBarData.setValue(totalSoldQty);
		actualavgBarData.setColor(CommonUtil.getGraphColors()[1]);
		avgDataDivis.addBarData(actualavgBarData);
		
		avgDataDivis.setDivisionTitle(divisionTitle);
		barChartData.addDivision(avgDataDivis);
		barChartData.setTitle(title);
		barChartData.setSubTitle(currentPeriod.getPeriod());
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax( (int)((currentPeriod.getTotalTarget()>totalSoldQty)?currentPeriod.getTotalTarget():totalSoldQty));
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		return barChartData;
		
	}
	
	@Override
	public BarChartData setDivisionSalesTargetData(User manager, Date date,
			CRMContext context, String classification) {
		SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
		ISalesPeriodService service = (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		ISalesService salesService =  (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		if (Utils.isNullString(classification) || "TOTAL".equalsIgnoreCase(classification) ) {
		int totalSoldQty = salesService.getTotalSaleQuantity(currentPeriod.getFromDate(),
				currentPeriod.getToDate(), currentPeriod.getDivision());
		
		return makeBarChartData(currentPeriod, totalSoldQty, "Total Sales Figures" ,"Sales Target state" );
		//int noAssociates  =getNoAssociates(associate, date, context) ;
		
		}else {
			if ("".equalsIgnoreCase(classification)) {
				
				//salesService.getCategorySaleQuantity(categoryId,currentPeriod.getFromDate(),currentPeriod.getToDate(), currentPeriod.getDivision());
				
			}
			return setDivisionSalesTargetData(manager, date, context, null);
		}
			
		
	}

	
	
	
}
