package com.rainbow.crm.saleslead.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;


public class SalesLeadExtendedController extends CRMTransactionController{

	ServletContext ctx;
	HttpServletResponse resp;
	
	@Override
	public PageResult submit(ModelObject object) {
		return null;
	}
	
	public ISalesLeadService getService() {
		ISalesLeadService serv = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		return serv;
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if("printquote".equals(actionParam)) {
			try {
			ISalesLeadService service= getService();
			byte[] byteArray = service.printQuotation((SalesLead) object) ;
			resp.setContentType("application/xls");
			resp.setHeader("Content-Disposition","attachment; filename=quote.pdf" );
			
			OutputStream responseOutputStream = resp.getOutputStream();
			responseOutputStream.write(byteArray);
			responseOutputStream.close();
			PageResult result = new PageResult();
			result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
			return result;
			}catch(Exception ex)
			{
				Logwriter.INSTANCE.error(ex);
			}
		}
		return super.submit(object, actionParam);
	}
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		ctx =  request.getServletContext() ;
		resp = response ;
		return CommonUtil.generateContext(request);
	}
	
	
	
	

}
