package com.rainbow.crm.feedback.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.feedback.model.FeedBack;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.feedback.service.IFeedBackService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class FeedBackValidator extends CRMValidator {

	FeedBack feedBack ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IFeedBackService service =(IFeedBackService)SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		FeedBack exist = (FeedBack)service.getByBusinessKey(feedBack, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IFeedBackService service =(IFeedBackService)SpringObjectFactory.INSTANCE.getInstance("IFeedBackService");
		FeedBack exist = (FeedBack)service.getByBusinessKey(feedBack, context);
		if(exist != null && exist.getId() != feedBack.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		feedBack = (FeedBack) object ;
		if (feedBack.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(feedBack.getFeedBackDate())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(feedBack.getFeedBackLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (FeedBackLine line : feedBack.getFeedBackLines()) {
				if (line.getFeedBackObjectType() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Type"))) ;
				}else if (line.getFeedBackObject() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Object") )) ;
				}
			}
		}
	}
	
	public FeedBackValidator(CRMContext context) {
		super(context);
	}
	public FeedBackValidator(){
		
	}

	
}
