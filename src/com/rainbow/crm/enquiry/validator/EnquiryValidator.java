package com.rainbow.crm.enquiry.validator;

import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.rainbow.crm.enquiry.service.IEnquiryService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class EnquiryValidator extends CRMValidator {

	Enquiry enquiry = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IEnquiryService  service = (IEnquiryService) SpringObjectFactory.INSTANCE.getInstance("IEnquiryService");
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
	}
	
	protected void checkforErrors(ModelObject object) {
		enquiry = (Enquiry) object;
		if(enquiry.getDivision()==null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Division"))) ;
		}else {
			enquiry.setDivision(CommonUtil.getDivisionObect(context, enquiry.getDivision()));
		}
		if(enquiry.getSalesAssociate() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Associate"))) ;
		}else {
			enquiry.setSalesAssociate(CommonUtil.getUser(context, enquiry.getSalesAssociate().getUserId()));
		}
		if (enquiry.getContact() != null){
			enquiry.setContact((Contact)CommonUtil.getCRMModelObject(context, enquiry.getContact(), "IContactService"));
		}
		if (enquiry.getTerritory() != null){
			enquiry.setTerritory((Territory)CommonUtil.getCRMModelObject(context, enquiry.getContact(), "ITerritoryService"));
		}
		if (enquiry.getNearestItem() != null){
			enquiry.setNearestItem((Item)CommonUtil.getCRMModelObject(context, enquiry.getNearestItem(), "IItemService"));
		}	
		if (enquiry.getNearestSku() != null){
			enquiry.setNearestSku((Sku)CommonUtil.getCRMModelObject(context, enquiry.getNearestSku(), "ISkuService"));
		}	
		
		
	}
	
	public EnquiryValidator(CRMContext context) {
		super(context);
	}
	
	public EnquiryValidator(){
		
	}

	
}
