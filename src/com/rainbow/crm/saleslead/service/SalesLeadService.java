package com.rainbow.crm.saleslead.service;

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
import com.rainbow.crm.saleslead.dao.SalesLeadDAO;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.validator.SalesLeadErrorCodes;
import com.rainbow.crm.saleslead.validator.SalesLeadValidator;
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
public class SalesLeadService extends AbstractionTransactionService implements ISalesLeadService{

	
	
	
	@Override
	public byte[] printQuotation(SalesLead lead) {
		try { 
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("TermsAndCond"," Goods once sold would not be taken back");
			parameters.put("FooterNote","Looking forward for business with you");
			parameters.put("HeaderNote","Please find the quotation based on our reference");
			parameters.put("LeadId", lead.getId());
			Connection connection  = ConnectionCreater.getConnection() ;
			URL resource = this.getClass().getResource("/jaspertemplates/QuotationFormat1.jrxml");
			
			JasperDesign jasperDesign = JRXmlLoader.load(resource.getPath());
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign); 
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	        byte[] output = JasperExportManager.exportReportToPdf(jasperPrint); 
	      // JasperViewer.viewReport(jasperPrint); 
	        return output; 
	        
			

		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return null;
	}

	@Override
	public long getTotalRecordCount(CRMContext context,String whereCondition) {
		String additionalCondition = getAdditionalCondition(whereCondition, context);
		return super.getTotalRecordCount(context, additionalCondition);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	protected String getTableName() {
		return "SalesLead";
	}
	
	private String getAdditionalCondition(String whereCondition, CRMContext context)
	{
		StringBuffer additionalCondition = new StringBuffer();
		if(CommonUtil.isManagerRole(context.getLoggedInUser() )) return whereCondition;
		if (Utils.isNullString(whereCondition) ){
			additionalCondition = additionalCondition.append(" where (  salesAssociate is null or  salesAssociate ='" + context.getUser() + "')") ; 
		}else {
			additionalCondition = additionalCondition.append(whereCondition + " and  (salesAssociate is null or  salesAssociate ='" + context.getUser() + "')") ;
		}
		
		String workableleads = context.getProperty("workableleads");
		if("true".equalsIgnoreCase(workableleads)) {
			additionalCondition = additionalCondition.append(whereCondition + " and ( status is null or status.code not  in ('CLSD','FLD')) " );
		}
		return additionalCondition.toString();
	}
	
	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		 String additionalCondition = getAdditionalCondition(whereCondition, context);
		
		return super.listData("SalesLead", from, to, additionalCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesLead)object).setCompany(company);
		SalesLeadValidator validator = new SalesLeadValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesLead)object).setCompany(company);
		SalesLeadValidator validator = new SalesLeadValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new SalesLeadDAO();
	return (SalesLeadDAO) SpringObjectFactory.INSTANCE.getInstance("SalesLeadDAO");
	}

	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context,ModelObject obj) {
		SalesLead object = (SalesLead) obj;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
		List<RadsError> ans = new ArrayList<RadsError>();
		
		
		if (object.getDivision() != null) {
			int divisionId  = object.getDivision().getId() ;
			IDivisionService divisionService =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0 )
				division = (Division)divisionService.getById(divisionId);
			else
				division  = (Division)divisionService.getByBusinessKey(object.getDivision(), context);
			if(division == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		if (object.getCustomer() != null) {
			String phone = object.getCustomer().getPhone();
			ICustomerService customerService = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
			Customer customer = customerService.getByPhone(context.getLoggedinCompany(), phone);
			if (customer != null)
				 object.setCustomer(customer);
		}
		
		if(object.getReferall() != null && !Utils.isNullString(object.getReferall().getIdentifierName()))
		{
			IContactService contactService = (IContactService) SpringObjectFactory.INSTANCE.getInstance("IContactService");
			Contact contact = contactService.getByFullName(context.getLoggedinCompany(), object.getReferall().getIdentifierName());
			if (contact != null)
				  object.setReferall(contact);
			else
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.FIELD_NOT_VALID , "Referral"));
	
		}
		
		Externalize externalize = new Externalize(); ;
		if(object.getTerritory() != null) {
			ITerritoryService territoryService = (ITerritoryService)SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
			Territory territory  = (Territory)territoryService.getById(object.getTerritory().getId());
			object.setTerritory(territory);
		}
		
		if(!Utils.isNullSet(object.getSalesLeadLines())){
			int lineNo=1;
			for (SalesLeadLine line: object.getSalesLeadLines()) {
				line.setCompany(company);
				line.setDocNumber(object.getDocNumber());
				line.setDivision(object.getDivision());
				line.setLineNumber(lineNo ++);
				if(line.getSku() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getSku().getName() ;
					ISkuService itemService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
					Sku item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setSku(item);
				}
			}
		}
		return ans;
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		SalesLead salesLead = (SalesLead)object ;
		if (Utils.isNull(salesLead.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("SalesLead", context, salesLead.getDivision());
			salesLead.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(salesLead.getSalesLeadLines())) {
			int pk = GeneralSQLs.getNextPKValue("SalesLead") ;
			salesLead.setId(pk);
			for (SalesLeadLine  line : salesLead.getSalesLeadLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "SalesLead_Lines") ;
				line.setId(linePK);
				line.setSalesLeadDoc(salesLead);
				line.setDocNumber(salesLead.getDocNumber());
			}
		}
		TransactionResult result= super.create(object, context);
		raiseAlert(salesLead, context);
		return result; 
	}

	  private void raiseAlert(SalesLead lead, CRMContext context) {
		  Alert alert = new Alert();
		  alert.setCompany(lead.getCompany());
		  alert.setType (new FiniteValue( CRMConstants.ALERT_TYPE.SALESLEAD));
		  alert.setActionDate(lead.getReleasedDate());
		  alert.setDivision(lead.getDivision());
		  if (!Utils.isNullString(lead.getSalesAssociate()))  {
		  User user  = CommonUtil.getUser(context, lead.getSalesAssociate());
		  alert.setOwner(user);
		  }
		  alert.setRaisedDate(new java.util.Date());
		  alert.setData("New Sales Lead-" +  lead.getDocNumber());
		  alert.setUrl("./rdscontroller?page=newsaleslead&id="+lead.getId() +"&hdnFixedAction=FixedAction.ACTION_GOEDITMODE");
		  CRMMessageSender.sendMessage(alert);
	  }
	
	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		SalesLead salesLead = (SalesLead)object ;
		SalesLead oldObject = (SalesLead)getById(salesLead.getPK());
		//SalesLead oldInvObj = (SalesLead)oldObject.clone();
		if (!Utils.isNullSet(salesLead.getSalesLeadLines())) {
			int  ct = 0;
			Iterator it = oldObject.getSalesLeadLines().iterator() ;
			for (SalesLeadLine  line : salesLead.getSalesLeadLines()) {
				SalesLeadLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (SalesLeadLine) it.next() ;
				}
				line.setSalesLeadDoc(salesLead);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SalesLead_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				SalesLeadLine oldLine= (SalesLeadLine) it.next() ;
				oldLine.setVoided(true);
				salesLead.addSalesLeadLine(oldLine);
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

	@Override
	public int getItemSaleQuantity(Sku item, Date from, Date to,Division division) {
		//SalesLeadDAO dao = (SalesLeadDAO)getDAO() ;
		return GeneralSQLs.getItemSoldQty(item.getId(),from,to,division.getId());
	}

	@Override
	public List<RadsError> startSalesCycle(SalesLead salesLead) {
		String k = CRMConstants.SALESCYCLE_STATUS.INITIATED;
		FiniteValue value = new FiniteValue();
		value.setCode(CRMConstants.SALESCYCLE_STATUS.INITIATED);
		value.setType("SLCYCSTS");
		salesLead.setStatus(value);
		getDAO().update(salesLead);
		return null;
	}

/*	@Override
	public List<RadsError> sendEmail(SalesLead salesLead, CRMContext context) {
		List<RadsError> errors = new ArrayList<RadsError>();
		String to = salesLead.getCustomer().getEmail();
		String from  = "noresponse@primussol.com";
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(properties);
		try {
			  MimeMessage message = new MimeMessage(session);
		      message.setFrom(new InternetAddress(from));
		      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.setSubject("This is the new util tets!");
	         message.setText("Testing email");
	         Transport.send(message);
		}catch(Exception ex){
			Logwriter.INSTANCE.error(ex);
			errors.add(new RadsError(String.valueOf(CommonErrorCodes.COULDNOT_SENDEMAIL),"Could not send email"));
		}
		return errors;

	}

*/	@Override
	public List<RadsError> sendEmail(SalesLead salesLead,CRMContext context,String realPath) {
	List<RadsError> errors = new ArrayList<RadsError>();
		try {
		
			String to = salesLead.getCustomer().getEmail();
			String from  = "noresponse@primussol.com";
			String host = CRMAppConfig.INSTANCE.getProperty("smtp_provider");
			String port =CRMAppConfig.INSTANCE.getProperty("smtp_port");
			String authuser =CRMAppConfig.INSTANCE.getProperty("smtp_authuser");
			String authpwd =CRMAppConfig.INSTANCE.getProperty("smtp_password");
			Properties properties = System.getProperties();
			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			
			Session session = Session.getInstance(properties,
			        new javax.mail.Authenticator() {
				
			            protected PasswordAuthentication getPasswordAuthentication() {
			                return new PasswordAuthentication(authuser, authpwd);
			            }
			        });
			 MimeMessage message = new MimeMessage(session);
			 BodyPart messageBodyPart = new MimeBodyPart();
			 messageBodyPart.setContent("<img>", "text/html");
		     message.setFrom(new InternetAddress(from));
		     message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.setSubject("Sale!!! Items you were looking for");
	         loadImages(salesLead, context,realPath);
	         String msg = getMessage(salesLead, context);
	         /**
	          * 
	          */
	         FileOutputStream fos =new FileOutputStream(realPath +"\\test.html");
	         fos.write("<HTML><BODY>".getBytes());
	         fos.write(msg.getBytes());
	         fos.write("</BODY></HTML>".getBytes());
	         fos.close(); 
	         message.setContent(msg, "text/html; charset=utf-8");
	         Transport t = session.getTransport("smtps");
	         t.connect(host,authuser, authpwd);
	         t.sendMessage(message, message.getAllRecipients());
		}catch(Exception ex){
			Logwriter.INSTANCE.error(ex);
			errors.add(new RadsError(String.valueOf(CommonErrorCodes.COULDNOT_SENDEMAIL),"Could not send email"));
		}
		return errors;

	}
	

 	private void loadImages(SalesLead salesLead,CRMContext context,String realPath) {
 		try {
 		for (SalesLeadLine line : salesLead.getSalesLeadLines() ) {
 			ItemImage image1 = ItemImageSQL.getItemImage(line.getSku().getId(), 'a');
 			ItemImage image2 = ItemImageSQL.getItemImage(line.getSku().getId(), 'b');
 			ItemImage image3 = ItemImageSQL.getItemImage(line.getSku().getId(), 'c');
 			String filePath = CRMAppConfig.INSTANCE.getProperty("doc_server");
			String code = context.getLoggedinCompanyCode();
			if (image1 != null && image1.getFileName() != null  ) {
				String fname1 =  filePath + "/" +  code  + "/itemimages/" + image1.getFileName();
				line.setImage1URL( fname1 );
				/*FileInputStream fis  = new FileInputStream(fname1);
				byte[] array1 = IOUtils.toByteArray(fis);
				line.setImgBytes1(array1);*/
				
			}
			if (image2 != null && image2.getFileName() != null  ) {
				String fname2 = filePath + "/" +  code  + "/itemimages/" + image2.getFileName();
				line.setImage2URL(  fname2);
				/*FileInputStream fis  = new FileInputStream(fname2);
				byte[] array = IOUtils.toByteArray(fis);
				line.setImgBytes2(array);*/
				
			}
			if (image3 != null && image3.getFileName() != null  ) {
				String fname3 = filePath + "/" +  code  + "/itemimages/" + image3.getFileName();
				line.setImage3URL(  fname3);
			/*	FileInputStream fis  = new FileInputStream(fname3);
				byte[] array = IOUtils.toByteArray(fis);
				line.setImgBytes3(array);*/
			}
			
 		}
 		}catch(Exception ex) {
 			Logwriter.INSTANCE.error(ex);
 		}
 	}
	
	private String  getMessage (SalesLead salesLead,CRMContext context) {
		VelocityEngine ve = new VelocityEngine();
        try {
        IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
        User user = (User)userService.getById(context.getUser());
        String path = CRMAppConfig.INSTANCE.getProperty("VelocityTemplatePath");
        ve.setProperty("file.resource.loader.path", path);
        ve.init();
        Template t = ve.getTemplate("salesLeadEmail.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("customerName", salesLead.getCustomer().getFirstName());
        velocityContext.put("companyName", salesLead.getCompany().getName());
        velocityContext.put("lines", salesLead.getSalesLeadLines());
        velocityContext.put("address1", salesLead.getDivision().getAddress1());
        velocityContext.put("address2", salesLead.getDivision().getAddress2());
        velocityContext.put("city", salesLead.getDivision().getCity());
        velocityContext.put("pin", salesLead.getDivision().getZipCode());
        velocityContext.put("storephone", salesLead.getDivision().getPhone());
        velocityContext.put("salesphone", user.getPhone());

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        return writer.toString();
        }catch(Exception ex){
        	Logwriter.INSTANCE.error(ex);
        }
        return "";

	}

	@Override
	public SalesLeadExtended getSalesLeadWithExtension(int leadId,CRMContext context) {
		SalesLead lead = (SalesLead)getById(leadId);
		String leadJSON = lead.toJSON();
		SalesLeadExtended extenstion  = (SalesLeadExtended) SalesLeadExtended.instantiateObjectfromJSON(leadJSON, 
				"com.rainbow.crm.saleslead.model.SalesLeadExtended", context);
		extenstion.setCustomer(lead.getCustomer());
		extenstion.setCompany(lead.getCompany());
		extenstion.setDivision(lead.getDivision());
		IFollowupService followUpService  = (IFollowupService)SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
		List<Followup> followups = followUpService.findBySalesLead(lead);
		extenstion.setFollowups(followups);	
		
		IDocumentService documentService  = (IDocumentService)SpringObjectFactory.INSTANCE.getInstance("IDocumentService");
		List<Document> documents =  documentService.findAllBySalesLead(lead);
		extenstion.setDocuments(documents);
		return extenstion;
	}
	
	
	
	
	
}
