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
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class SalesDashBoardController extends CRMGeneralController{
	
	String graphId;
	
	static final String TARGET_ANALYSIS = "targetanalysis" ;
	static final String PORTFOLIO_SPLITS = "portfoliosplits" ;
	static final String SALES_HISTORY = "saleshistory" ;
	static final String LEAD_SPLITS = "leadSplits" ;

	static final String DIV_TARGET_ANALYSIS = "divtargetanalysis" ;
	static final String DIV_ASSOC_SALES_SPLITS = "divassocsalessplits" ;
	static final String DIV_PROD_SALES_SPLITS = "divprodsalessplits" ;
	static final String DIV_MGR_SALELEADSPLITS="divmgrsalesleadsplits";
	static final String DIV_SALES_HISTORY="divsaleshistory";
	
	@Override
	public PageResult submit(ModelObject object) {
		SalesDashBoard dashBoard = (SalesDashBoard) object;
		IDashBoardService service = (IDashBoardService) SpringObjectFactory.INSTANCE.getInstance("IDashBoardService");
		if(TARGET_ANALYSIS.equalsIgnoreCase(graphId))  {
		BarChartData barChartData = service.setSalesTargetData(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesTargetData(barChartData);
		}
		
		if(DIV_TARGET_ANALYSIS.equalsIgnoreCase(graphId))  {
			String classification = dashBoard.getClassification() ;
			BarChartData barChartData = service.setDivisionSalesTargetData(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(),
					(CRMContext)getContext(),classification);
			dashBoard.setDivManagersalesTargetData(barChartData);
		}
		
		
		if(PORTFOLIO_SPLITS.equalsIgnoreCase(graphId))  {
		PieChartData pieChartData  = service.getPortfolioSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setPortfolioSplits(pieChartData);
		}
		if (LEAD_SPLITS.equalsIgnoreCase(graphId)) {
			PieChartData pieChartData  = service.getLeadSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
			dashBoard.setLeadSplits(pieChartData);
		}
		
		if(DIV_MGR_SALELEADSPLITS.equalsIgnoreCase(graphId)) {
			PieChartData pieChartData  = service.getDivisionLeadSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
			dashBoard.setDivManagersalesleadSplits(pieChartData);
		}
		
		
		if(DIV_ASSOC_SALES_SPLITS.equalsIgnoreCase(graphId))  {
			String type = dashBoard.getSalespiecriteria() ;
			if(Utils.isNullString(type)  || "ASSOCIATE".equals(type)) {
				PieChartData pieChartData  = service.getAssociateSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("TERRITORY".equals(type)) {
				PieChartData pieChartData  = service.getTerritorySplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}else if( "PRODUCT".equals(type)) {
				PieChartData pieChartData  = service.getProductwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("ITEM".equals(type)) {
				PieChartData pieChartData  = service.getItemwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("CATEGORY".equals(type)) {
				PieChartData pieChartData  = service.getCategorywiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}else if("BRAND".equals(type)) {
				PieChartData pieChartData  = service.getBrandwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}
		}
		
		if(DIV_PROD_SALES_SPLITS.equalsIgnoreCase(graphId))  {
			String type = dashBoard.getSalespiecriteria() ; 
			if(Utils.isNullString(type)  || "PRODUCT".equals(type)) {
				PieChartData pieChartData  = service.getProductwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			} else if("ITEM".equals(type)) {
				PieChartData pieChartData  = service.getItemwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			} else if("CATEGORY".equals(type)) {
				PieChartData pieChartData  = service.getCategorywiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			}else if("BRAND".equals(type)) {
				PieChartData pieChartData  = service.getBrandwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			}
		}
		
		if(SALES_HISTORY.equalsIgnoreCase(graphId))  {
		LineChartData lineChartData = service.getSalesHistory(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesHistory(lineChartData);
		}
		
		if (DIV_SALES_HISTORY.equalsIgnoreCase(graphId))  {
		LineChartData lineChartData = service.getDivSalesHistory(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setDivSalesHistory(lineChartData);
		}
		
		
		return new PageResult();
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response,UIPage page) { 
		graphId= request.getParameter("graphId");
		return super.generateContext(request, response,page);
	}

	
	
}


