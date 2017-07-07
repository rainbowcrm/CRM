package com.rainbow.crm.feedback.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.contact.service.IContactService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.document.service.IDocumentService;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.service.IFollowupService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.feedback.dao.FeedBackDAO;
import com.rainbow.crm.feedback.model.FeedBack;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.feedback.validator.FeedBackErrorCodes;
import com.rainbow.crm.feedback.validator.FeedBackValidator;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class FeedBackService extends AbstractionTransactionService implements IFeedBackService{

	
	
	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		
		return super.listData("FeedBack", from, to, whereCondition, context,sortCriteria);
	}

	
	@Override
	public long getTotalRecordCount(CRMContext context,String whereCondition) {
		return super.getTotalRecordCount(context, whereCondition);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	protected String getTableName() {
		return "FeedBack";
	}
	
	

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((FeedBack)object).setCompany(company);
		FeedBackValidator validator = new FeedBackValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((FeedBack)object).setCompany(company);
		FeedBackValidator validator = new FeedBackValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new FeedBackDAO();
	return (FeedBackDAO) SpringObjectFactory.INSTANCE.getInstance("FeedBackDAO");
	}

	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context,ModelObject obj) {
		FeedBack object = (FeedBack) obj;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
		List<RadsError> ans = new ArrayList<RadsError>();
		
		Externalize externalize = new Externalize(); 
		if (object.getDivision() != null) {
			int divisionId  = object.getDivision().getId() ;
			IDivisionService divisionService =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0 )
				division = (Division)divisionService.getById(divisionId);
			else
				division  = (Division)divisionService.getByBusinessKey(object.getDivision(), context);
			if(division == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), FeedBackErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		if (object.getCapturedBy() != null) {
			IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
			User user = (User) userService.getById(object.getCapturedBy().getUserId());
			if(user != null)
				object.setCapturedBy(user);
			else
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), FeedBackErrorCodes.FIELD_NOT_VALID , "Captured_By"));
			
		}
		if (object.getCustomer() != null) {
			String phone = object.getCustomer().getPhone();
			ICustomerService customerService = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
			Customer customer = customerService.getByPhone(context.getLoggedinCompany(), phone);
			if (customer != null)
				 object.setCustomer(customer);
		}
		if(object.getSales() != null && !Utils.isNullString(object.getSales().getBillNumber())) {
			ISalesService salesService = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
			Sales sales = (Sales )salesService.getByBusinessKey( object.getSales(),context );
			if(sales != null)
				object.setSales(sales);
			else
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), FeedBackErrorCodes.FIELD_NOT_VALID , "Sales_Bill"));
			
		}
				
		if(!Utils.isNullSet(object.getFeedBackLines())){
			int lineNo=1;
			for (FeedBackLine line: object.getFeedBackLines()) {
				line.setCompany(company);
				line.setLineNumber(lineNo ++);
				if(line.getFeedBackObjectType() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), FeedBackErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Type")));
				}else if (line.getFeedBackObjectType().equals(CRMConstants.FEEDBACK_ON.SALES_ASSOCIATE)) {
					IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
					 User user = (User) userService.getById(line.getFeedBackObject());
					 if(user != null)
						 line.setAssociate(user);
					 else
						ans.add(CRMValidator.getErrorforCode(context.getLocale(), FeedBackErrorCodes.FIELD_NOT_VALID , "Associate"));
				}else if (line.getFeedBackObjectType().equals(CRMConstants.FEEDBACK_ON.SALES_LINE)) {
					ISkuService skuService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
					Sku sku = (Sku)skuService.getById(line.getFeedBackObject());
					if(sku != null)
						line.setSku(sku);
					else
						ans.add(CRMValidator.getErrorforCode(context.getLocale(), FeedBackErrorCodes.FIELD_NOT_VALID , "Sku"));
				}
				if(line.getReasonCode() != null ) {
					IReasonCodeService reasonCodeService = (IReasonCodeService)SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService");
					ReasonCode reasonCode = (ReasonCode) reasonCodeService.getById(line.getReasonCode().getId());
					if(reasonCode != null)
						line.setReasonCode(reasonCode);
					else
						ans.add(CRMValidator.getErrorforCode(context.getLocale(), FeedBackErrorCodes.FIELD_NOT_VALID , "ReasonCode"));
				}
				
			}
		}
		return ans;
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		FeedBack feedBack = (FeedBack)object ;
		if (Utils.isNull(feedBack.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("FeedBack", context, feedBack.getDivision());
			feedBack.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(feedBack.getFeedBackLines())) {
			int pk = GeneralSQLs.getNextPKValue("FeedBack") ;
			feedBack.setId(pk);
			for (FeedBackLine  line : feedBack.getFeedBackLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "FeedBack_Lines") ;
				line.setId(linePK);
				line.setFeedBackDoc(feedBack);
			}
		}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	
	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		FeedBack feedBack = (FeedBack)object ;
		FeedBack oldObject = (FeedBack)getById(feedBack.getPK());
		//FeedBack oldInvObj = (FeedBack)oldObject.clone();
		if (!Utils.isNullSet(feedBack.getFeedBackLines())) {
			int  ct = 0;
			Iterator it = oldObject.getFeedBackLines().iterator() ;
			for (FeedBackLine  line : feedBack.getFeedBackLines()) {
				FeedBackLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (FeedBackLine) it.next() ;
				}
				line.setFeedBackDoc(feedBack);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "FeedBack_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				FeedBackLine oldLine= (FeedBackLine) it.next() ;
				oldLine.setDeleted(true);
				feedBack.addFeedBackLine(oldLine);
			}
		}
		return super.update(object, context);
	}

	@Override
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchUpdate(objects, context);
	}

	@Override
	public TransactionResult batchCreate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchCreate(objects, context);
	}

		
	
	
	
	
}
