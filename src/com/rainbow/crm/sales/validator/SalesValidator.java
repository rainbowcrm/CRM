package com.rainbow.crm.sales.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SalesValidator extends CRMValidator {

	Sales sales ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesService service =(ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales exist = (Sales)service.getByBusinessKey(sales, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISalesService service =(ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales exist = (Sales)service.getByBusinessKey(sales, context);
		if(exist != null && exist.getId() != sales.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Bill_No"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		sales = (Sales) object ;
		if (sales.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(sales.getSalesDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(sales.getSalesLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (SalesLine line : sales.getSalesLines()) {
				if (line.getItem() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getItem().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getItem().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Qty") + line.getItem().getCode(),"0") ) ;
				}else if (line.getUnitPrice() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Unit_Price") + line.getItem().getCode(),"0") ) ;
				}
			}
		}
	}
	
	public SalesValidator(CRMContext context) {
		super(context);
	}
	public SalesValidator(){
		
	}

	
}
