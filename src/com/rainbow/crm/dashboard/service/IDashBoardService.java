package com.rainbow.crm.dashboard.service;

import java.util.Date;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;

public interface IDashBoardService {

	public BarChartData setSalesTargetData(User associate,Date date, CRMContext context  );
	
	public LineChartData getSalesHistory(User associate,Date date, CRMContext context  );
	
	public LineChartData getDivSalesHistory(User manager,Date date, CRMContext context  );
	
	public PieChartData getPortfolioSplits(User associate,Date date, CRMContext context  );
	
	public PieChartData getLeadSplits(User associate,Date date, CRMContext context  );
	
	public PieChartData getDivisionLeadSplits(User manager,Date date, CRMContext context  );
	
	public PieChartData getProductwiseSales(User manager,Date date, CRMContext context  );
	
	public PieChartData getItemwiseSales(User manager,Date date, CRMContext context  );
	
	public PieChartData getCategorywiseSales(User manager,Date date, CRMContext context  );
	
	public PieChartData getBrandwiseSales(User manager,Date date, CRMContext context  );
	
	public PieChartData getAssociateSplits(User manager,Date date, CRMContext context  );
	
	public PieChartData getTerritorySplits(User manager,Date date, CRMContext context  );
	
	public BarChartData setDivisionSalesTargetData(User manager,Date date, CRMContext context, String classification  );
	
}
