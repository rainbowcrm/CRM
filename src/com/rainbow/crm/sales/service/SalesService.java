package com.rainbow.crm.sales.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.address.service.IAddressService;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.documents.PrintDocument;
import com.rainbow.crm.common.documents.PrintField;
import com.rainbow.crm.common.documents.PrintHeaderLine;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.sales.dao.SalesDAO;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.validator.SalesErrorCodes;
import com.rainbow.crm.sales.validator.SalesValidator;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class SalesService extends AbstractService implements ISalesService{

	@Override
	public long getTotalRecordCount(CRMContext context) {
		return getDAO().getTotalRecordCount("Sales",context);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context) {
		return super.listData("Sales", from, to, whereCondition, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Sales)object).setCompany(company);
		SalesValidator validator = new SalesValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Sales)object).setCompany(company);
		SalesValidator validator = new SalesValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new SalesDAO();
	return (SalesDAO) SpringObjectFactory.INSTANCE.getInstance("SalesDAO");
	}

	
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context, (Sales)object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,Sales object) {
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
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.FIELD_NOT_VALID , "Division"));
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
		Externalize externalize = new Externalize(); ;
		
		if(!Utils.isNullSet(object.getSalesLines())){
			int lineNo=1;
			for (SalesLine line: object.getSalesLines()) {
				line.setCompany(company);
				line.setBillNumber(object.getBillNumber());
				line.setLineNumber(lineNo ++);
				if(line.getSku() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getSku().getName() ;
					ISkuService itemService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
					Sku item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setSku(item);
				}
			}
		}
		if(object.getDeliveryAddress() != null  && !object.getDeliveryAddress().isNullContent()) {
			IAddressService addService = (IAddressService)SpringObjectFactory.INSTANCE.getInstance("IAddressService");
			Address delivery =(Address) addService.getByBusinessKey(object.getDeliveryAddress(), context);
			if (delivery != null &&  !delivery.isNullContent())
				object.setDeliveryAddress(delivery);
			else  {
				object.getDeliveryAddress().setCompany(object.getCompany());
				object.getDeliveryAddress().setAddressType(new FiniteValue(CRMConstants.ADDRESS_TYPE.PRIMARY_SHIPPING));
				int pk = GeneralSQLs.getNextPKValue("Addresses") ;
				object.getDeliveryAddress().setId(pk);
			}
				
		}
		return ans;
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Sales sales = (Sales)object ;
		
		if (Utils.isNull(sales.getBillNumber())) {
			String bKey = NextUpGenerator.getNextNumber("Sales", context, sales.getDivision());
			sales.setBillNumber(bKey);
		}
		if (!Utils.isNullSet(sales.getSalesLines())) {
			int pk = GeneralSQLs.getNextPKValue("Sales") ;
			sales.setId(pk);
			for (SalesLine  line : sales.getSalesLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "Sales_Lines") ;
				line.setId(linePK);
				line.setSalesDoc(sales);
				line.setBillNumber(sales.getBillNumber());
			}
		}
		TransactionResult result= super.create(object, context);
		if (sales.isFutureDelivery()) {
		InventoryUpdateObject invObject = new InventoryUpdateObject();
		invObject.setCompany(sales.getCompany());
		invObject.setContext(context);
		invObject.setDivision(sales.getDivision());
		invObject.setAddition(false);
		invObject.setItemLines(sales.getSalesLines());
		CRMMessageSender.sendMessage(invObject);
		}
		
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Sales sales = (Sales)object ;
		Sales oldObject = (Sales)getById(sales.getPK());
		//Sales oldInvObj = (Sales)oldObject.clone();
		if (!Utils.isNullSet(sales.getSalesLines())) {
			int  ct = 0;
			Iterator it = oldObject.getSalesLines().iterator() ;
			for (SalesLine  line : sales.getSalesLines()) {
				SalesLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (SalesLine) it.next() ;
				}
				line.setSalesDoc(sales);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "Sales_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				SalesLine oldLine= (SalesLine) it.next() ;
				oldLine.setVoided(true);
				sales.addSalesLine(oldLine);
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
	public int getItemSaleQuantity(Item item, Date from, Date to,Division division) {
		return GeneralSQLs.getItemSoldQty(item.getId(),from,to,division.getId());
	}

	@Override
	public Map getItemSoldQtyByProduct(Product product, Date from, Date to,  Division division , String itemClass) {
		return GeneralSQLs.getItemSoldQtyByProduct(product.getId(), from, to, -1,itemClass);
	}

	
	
	@Override
	public String generateInvoice(Sales sales,CRMContext context) {
		VelocityEngine ve = new VelocityEngine();
		Externalize externalize = new Externalize();
        try {
        IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
        User user = (User)userService.getById(context.getUser());
        String path = CRMAppConfig.INSTANCE.getProperty("VelocityTemplatePath");
        ve.setProperty("file.resource.loader.path", path);
        ve.init();
        Template t = ve.getTemplate("salesInvoice.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("companyName", sales.getCompany().getName());
        velocityContext.put("title", externalize.externalize(context, "Sales_Invoice"));
        velocityContext.put("billField", externalize.externalize(context, "Bill_No"));
        velocityContext.put("dateField", externalize.externalize(context, "Date"));
        velocityContext.put("custField", externalize.externalize(context, "Customer"));
        velocityContext.put("taxField", externalize.externalize(context, "Tax"));
        velocityContext.put("totalField", externalize.externalize(context, "Total"));
        velocityContext.put("deliveryField", externalize.externalize(context, "Delivery_Address"));

        velocityContext.put("billNo", sales.getBillNumber());
        velocityContext.put("saleDate", sales.getSalesDate());
        velocityContext.put("custName", sales.getCustomer()==null?"":sales.getCustomer().getFullName());
        velocityContext.put("address1", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getAddress1());
        velocityContext.put("address2", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getAddress2());
        velocityContext.put("city", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getCity());
        velocityContext.put("pincode", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getZipcode());
        velocityContext.put("phone", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getPhone());
        velocityContext.put("saleDate", sales.getSalesDate());
        velocityContext.put("taxAmount", sales.getTaxAmount());
        velocityContext.put("totalAmount", sales.getNetAmount());
        velocityContext.put("lines", sales.getSalesLines());

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        String content=  writer.toString();
        return content;
        
        }catch(Exception ex){
        	Logwriter.INSTANCE.error(ex);
        }

        return "";
		
	}

	private PrintDocument getPrintDocument(Sales sales,CRMContext context) throws Exception{
		PrintDocument printDocument = new PrintDocument();
		Externalize externalize = new Externalize();
		printDocument.setTitle(sales.getCompany().getName());
		printDocument.setSubTitle(externalize.externalize(context, "Sales_Invoice"));
		String billNoField =externalize.externalize(context, "Bill_No");
		String dateField =externalize.externalize(context, "Date");
		PrintHeaderLine headerLine1 = new PrintHeaderLine();
		headerLine1.addPrintField(new PrintField(billNoField,sales.getBillNumber()));
		printDocument.addHeaderLine(headerLine1);
		PrintHeaderLine headerLine2 = new PrintHeaderLine();
		headerLine2.addPrintField(new PrintField(dateField,Utils.dateToString(sales.getSalesDate(),externalize.getDateFormat())));
		printDocument.addHeaderLine(headerLine2);
		if (sales.getDeliveryAddress() != null) {
			PrintHeaderLine deliveryLine  = new PrintHeaderLine();
			deliveryLine.addPrintField(new PrintField("Delivery",""));
		}
		
		return printDocument;
	}
	
	
	
	
}
