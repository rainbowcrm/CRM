package com.rainbow.crm.sales.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.distributionorder.service.IDistributionOrderService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesController extends CRMTransactionController{
	
	ServletContext ctx;
	HttpServletResponse resp;
	
	
	
	
	@Override
	public PageResult submit(ModelObject object) {
		return super.submit(object);
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if("createDO".equals(actionParam)) {
			IDistributionOrderService distributionservice = (IDistributionOrderService)SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService") ;
			Sales sales =(Sales)getService().getById(object.getPK());
			distributionservice.createDOfromSalesOrder(sales, (CRMContext)getContext()) ;
			return new PageResult();
		}else 
			return super.submit(object, actionParam);
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response) {
		ctx =  request.getServletContext() ;
		resp = response ;
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}
	
	public ISalesService getService() {
		ISalesService serv = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		return serv;
	}


	public Map <String, String > getDeliveryModes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_DELIVERY_MODE);
		return ans;
	}
	
	public Map <String, String > getOrderTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ORDERTYPE);
		return ans;
	}
	
	@Override
	public PageResult print() {
		PageResult result  = new PageResult();
		try {
		ISalesService salesService = getService();
		String htmlData = salesService.generateInvoice((Sales) object,(CRMContext)getContext());
        OutputStream responseOutputStream = resp.getOutputStream();
        
        resp.setContentType("application/html");
		resp.setHeader("Content-Disposition","attachment; filename=inv.html" );
        responseOutputStream.write(htmlData.getBytes());
        responseOutputStream.close();
        result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return result;
	}
	
	public Map <String, String > getAllDivisions() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		IDivisionService service =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(((CRMContext)getContext()).getLoggedinCompany());
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}

}
