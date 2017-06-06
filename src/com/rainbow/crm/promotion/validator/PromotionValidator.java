package com.rainbow.crm.promotion.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.service.IPromotionService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class PromotionValidator extends CRMValidator {

	Promotion promotion = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IPromotionService  service = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		Promotion  exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IPromotionService  service = (IPromotionService) SpringObjectFactory.INSTANCE.getInstance("IPromotionService");
		Promotion  exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		exist = (Promotion)service.getByBusinessKey(promotion, context);
		if(exist != null  && exist.getId() != promotion.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Name"))) ;
		}
		
	}
	
	private void validateBundlingConditions() 
	{
		
	}
	
	private void validateCrossSellingConditions() 
	{
		
	}
	
	protected void checkforErrors(ModelObject object) {
		promotion = (Promotion) object;
		
	}
	
	public PromotionValidator(CRMContext context) {
		super(context);
	}
	
	public PromotionValidator(){
		
	}

	
}
