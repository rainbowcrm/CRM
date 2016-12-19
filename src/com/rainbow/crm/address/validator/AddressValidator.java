package com.rainbow.crm.address.validator;

import com.rainbow.crm.user.validator.UserErrorCodes;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.address.model.Address;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class AddressValidator extends CRMValidator {

	Address Address = null;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		
		
	}
	
	protected void checkforErrors(ModelObject object) {
		Address = (Address) object;
		System.out.println("Address XML=" + Address.toXML());
		if (Utils.isNull(Address.getEmail())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Email"))) ;
		}
		if (Utils.isNull(Address.getPhone())) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Phone"))) ;
		}
		if (!Utils.isNull(Address.getEmail()) && (!Address.getEmail().contains("@") || !Address.getEmail().contains(".")) ) {
			errors.add(getErrorforCode(UserErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Email"))) ;
		}
	}
	
	public AddressValidator(CRMContext context) {
		super(context);
	}
	
	public AddressValidator(){
		
	}

	
}
