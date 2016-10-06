package com.rainbow.crm.saleslead.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SalesLeadValidator extends CRMValidator {

	SalesLead salesLead ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesLeadService service =(ISalesLeadService)SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		SalesLead exist = (SalesLead)service.getByBusinessKey(salesLead, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesLeadService service =(ISalesLeadService)SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		SalesLead exist = (SalesLead)service.getByBusinessKey(salesLead, context);
		if(exist != null && exist.getId() != salesLead.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		salesLead = (SalesLead) object ;
		if (salesLead.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(salesLead.getReleasedDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(salesLead.getSalesLeadLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (SalesLeadLine line : salesLead.getSalesLeadLines()) {
				if (line.getItem() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getItem().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getItem().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Qty") + line.getItem().getCode(),"0") ) ;
				}else if (line.getPrice() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Price") + line.getItem().getCode(),"0") ) ;
				}
			}
		}
	}
	
	public SalesLeadValidator(CRMContext context) {
		super(context);
	}
	public SalesLeadValidator(){
		
	}

	
}
