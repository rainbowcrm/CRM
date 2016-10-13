package com.rainbow.crm.followup.validator;

import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.service.IFollowupService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class FollowupValidator extends CRMValidator {

	Followup followup = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IFollowupService  service = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IFollowupService  service = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
		
		
	}
	
	protected void checkforErrors(ModelObject object) {
		followup = (Followup) object;
		System.out.println("Cust XML=" + followup.toXML());
		if(followup.getLead()==null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Sales_Lead"))) ;
		}
		if(Utils.isNullString(followup.getSalesAssociate())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "SalesAssociate"))) ;
		}
		if (Utils.isNull(followup.getCommunicationMode())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Communication_Mode"))) ;
		}
		if (Utils.isNull(followup.getConfidenceLevel())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Confidence_Level"))) ;
		}
		
	}
	
	public FollowupValidator(CRMContext context) {
		super(context);
	}
	
	public FollowupValidator(){
		
	}

	
}
