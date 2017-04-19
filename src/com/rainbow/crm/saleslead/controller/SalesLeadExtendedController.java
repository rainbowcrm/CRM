package com.rainbow.crm.saleslead.controller;

import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

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
			ISalesLeadService service= getService();
			OutputStream stream =service.printQuotation((SalesLead) object) ;
			resp.setContentType("application/xls");
			resp.setHeader("Content-Disposition","attachment; filename=quote.pdf" );
			//OutputStream responseOutputStream = resp.getOutputStream();
            /*int bytes;
            stream.b
            while ((bytes = stream.read()) != -1) {
                responseOutputStream.write(bytes);
            }*/
		}
		return super.submit(object, actionParam);
	}
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response) {
		ctx =  request.getServletContext() ;
		resp = response ;
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}
	
	
	
	

}
