package com.rainbow.crm.salesperiod.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SalesPeriodValidator extends CRMValidator {

	SalesPeriod salesPeriod ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesPeriodService service =(ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		SalesPeriod exist = (SalesPeriod)service.getByBusinessKey(salesPeriod, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesPeriodService service =(ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		SalesPeriod exist = (SalesPeriod)service.getByBusinessKey(salesPeriod, context);
		if(exist != null && exist.getId() != salesPeriod.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_No"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		salesPeriod = (SalesPeriod) object ;
		if (salesPeriod.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(salesPeriod.getPeriod()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Period"))) ;
		}
		if(Utils.isNull(salesPeriod.getFromDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "From_Date"))) ;
		}
		if(Utils.isNull(salesPeriod.getToDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "To_Date"))) ;
		}
		if (Utils.isNullSet(salesPeriod.getSalesPeriodLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (SalesPeriodLine line : salesPeriod.getSalesPeriodLines()) {
				if (line.getItem() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getItem().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getItem().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Target_Qty") + line.getItem().getCode(),"0") ) ;
				}
			}
		}
		
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodAsssociates())){
			for (SalesPeriodAssociate line: salesPeriod.getSalesPeriodAsssociates()) {
				if (line.getUser() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getUser().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getUser().getUserId())) ;
				}else if (line.getLineTotal() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Total") + line.getUser().getUserId(),"0") ) ;
				}
				
			}
			
		}
	}
	public SalesPeriodValidator(CRMContext context) {
		super(context);
	}
	public SalesPeriodValidator(){
		
	}

	
}
