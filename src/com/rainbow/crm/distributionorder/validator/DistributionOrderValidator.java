package com.rainbow.crm.distributionorder.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.rainbow.crm.distributionorder.model.DistributionOrderLine;
import com.rainbow.crm.distributionorder.service.IDistributionOrderService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class DistributionOrderValidator extends CRMValidator {

	DistributionOrder distributionOrder ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IDistributionOrderService service =(IDistributionOrderService)SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService");
		DistributionOrder exist = (DistributionOrder)service.getByBusinessKey(distributionOrder, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IDistributionOrderService service =(IDistributionOrderService)SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService");
		DistributionOrder exist = (DistributionOrder)service.getByBusinessKey(distributionOrder, context);
		if(exist != null && exist.getId() != distributionOrder.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		distributionOrder = (DistributionOrder) object ;
		if (distributionOrder.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(distributionOrder.getOrderDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(distributionOrder.getDistributionOrderLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (DistributionOrderLine line : distributionOrder.getDistributionOrderLines()) {
				if (line.getItem() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
				}else if (line.getItem().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Item") + line.getItem().getCode())) ;
				}else if (line.getQty() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Qty") + line.getItem().getCode(),"0") ) ;
				}
			}
		}
	}
	
	public DistributionOrderValidator(CRMContext context) {
		super(context);
	}
	public DistributionOrderValidator(){
		
	}

	
}
