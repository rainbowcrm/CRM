package com.rainbow.crm.dashboard.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public class SalesDashBoard extends CRMModelObject{
	
	BarChartData salesTargetData  ;
	LineChartData salesHistory;
	PieChartData portfolioSplits;
	
	
	public BarChartData getSalesTargetData() {
		return salesTargetData;
	}
	public void setSalesTargetData(BarChartData salesTargetData) {
		this.salesTargetData = salesTargetData;
	}
	public LineChartData getSalesHistory() {
		return salesHistory;
	}
	public void setSalesHistory(LineChartData salesHistory) {
		this.salesHistory = salesHistory;
	}
	public PieChartData getPortfolioSplits() {
		return portfolioSplits;
	}
	public void setPortfolioSplits(PieChartData portfolioSplits) {
		this.portfolioSplits = portfolioSplits;
	}
		
	

}
