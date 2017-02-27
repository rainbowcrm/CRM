package com.rainbow.crm.dashboard.controller;

import java.util.Date;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.dashboard.model.SalesDashBoard;
import com.rainbow.crm.dashboard.service.IDashBoardService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalesDashBoardController extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		SalesDashBoard dashBoard = (SalesDashBoard) object;
		IDashBoardService service = (IDashBoardService) SpringObjectFactory.INSTANCE.getInstance("IDashBoardService");
		BarChartData barChartData = service.setSalesTargetData(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesTargetData(barChartData);
		
		PieChartData pieChartData  = service.getPortfolioSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setPortfolioSplits(pieChartData);
		
		LineChartData lineChartData = service.getSalesHistory(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesHistory(lineChartData);
		return new PageResult();
	}

	
	
}


