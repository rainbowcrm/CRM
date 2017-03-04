package com.rainbow.crm.dashboard.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.dashboard.model.SalesDashBoard;
import com.rainbow.crm.dashboard.service.IDashBoardService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class SalesDashBoardController extends CRMGeneralController{
	
	String graphId;
	
	@Override
	public PageResult submit(ModelObject object) {
		SalesDashBoard dashBoard = (SalesDashBoard) object;
		IDashBoardService service = (IDashBoardService) SpringObjectFactory.INSTANCE.getInstance("IDashBoardService");
		if("targetanalysis".equalsIgnoreCase(graphId))  {
		BarChartData barChartData = service.setSalesTargetData(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesTargetData(barChartData);
		}
		
		if("portfoliosplits".equalsIgnoreCase(graphId))  {
		PieChartData pieChartData  = service.getPortfolioSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setPortfolioSplits(pieChartData);
		}
		
		if("saleshistory".equalsIgnoreCase(graphId))  {
		LineChartData lineChartData = service.getSalesHistory(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesHistory(lineChartData);
		}
		return new PageResult();
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response) { 
		graphId= request.getParameter("graphId");
		return super.generateContext(request, response);
	}

	
	
}


