package com.rainbow.crm.common.scheduler;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.alert.service.IAlertService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.techtrade.rads.framework.utils.Utils;

public class SchedulerThread extends Thread{

	int interval ;
	
	
	private CRMContext makeContext(CRMBusinessModelObject model) {
		CRMContext context = new CRMContext();
		context.setLoggedinCompany(model.getCompany().getId());
		context.setAuthenticated(true);
		context.setUser(model.getLastUpdateUser());
		return context ;
	}
	
	private void createAlertforSalesPeriod(SalesPeriod period,CRMContext context, FiniteValue alertType) {
		IAlertService service = (IAlertService)SpringObjectFactory.INSTANCE.getInstance("IAlertService");
		Alert alert = new Alert();
		  alert.setCompany(period.getCompany());
		  alert.setType (alertType);
		  alert.setActionDate(new java.util.Date());
		  alert.setDivision(period.getDivision());
		  alert.setRaisedDate(new java.util.Date());
		  alert.setData("Sales Targetting Start-" +  period.getPeriod());
		  alert.setUrl("./rdscontroller?page=newsalesperiod&id="+period.getId() +"&hdnFixedAction=FixedAction.ACTION_GOEDITMODE");
		  alert.setStatus(new FiniteValue(CRMConstants.ALERT_STATUS.OPEN));
		  service.create(alert, context);
	}
	
	
	@Override
	public void run() 
	{
		
		
	}
	
	private void raiseSalesPeriodAlerts () {
		
		ISalesPeriodService service = (ISalesPeriodService) SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService") ;
		List<SalesPeriod> startingPeriods = service.getStartingSalesPeriodsforAlerts(new java.util.Date());
		if(!Utils.isNullList(startingPeriods)) {
			for (SalesPeriod salesPeriod :  startingPeriods) {
				CRMContext context =makeContext(salesPeriod);
				createAlertforSalesPeriod(salesPeriod,context,new FiniteValue(CRMConstants.ALERT_TYPE.SLSPERSTART));
				salesPeriod.setStartAlerted(true);
				service.update(salesPeriod, context);
			}
		}
		List<SalesPeriod> endingPeriods = service.getEndSalesPeriodsforAlerts(new java.util.Date());
		if(!Utils.isNullList(endingPeriods)) {
			for (SalesPeriod salesPeriod :  endingPeriods) {
				CRMContext context =makeContext(salesPeriod);
				createAlertforSalesPeriod(salesPeriod,context,new FiniteValue(CRMConstants.ALERT_TYPE.SLSPEREND));
				salesPeriod.setEndAlerted(true);
				service.update(salesPeriod, context);
			}
		}
	}
	
	public SchedulerThread() {
		try {
		String inte = CRMAppConfig.INSTANCE.getProperty("Thread_Interval");
		interval = Integer.parseInt(inte);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
