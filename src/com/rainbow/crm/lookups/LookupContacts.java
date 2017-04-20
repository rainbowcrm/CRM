package com.rainbow.crm.lookups;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.contact.service.IContactService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.utils.Utils;

public class LookupContacts implements ILookupService{

	@Override
	public List<Object> lookupData(IRadsContext ctx, String searchString,
			int from, int noRecords, String lookupParam) {
		List<Object> ans = new ArrayList<Object>();
		String condition = null;
		if (!Utils.isNull(searchString)) { 
			searchString = searchString.replace("*", "%");
			condition =  " where firstName like  '" + searchString + "' or  lastName like  '" + searchString + "' or phone like '" + searchString + "'" ;
		}
		IContactService service = (IContactService) SpringObjectFactory.INSTANCE.getInstance("IContactService");
		List<? extends CRMModelObject> contacts = service.listData(from, from  + noRecords, condition,(CRMContext)ctx,null);
		for (ModelObject obj :  contacts) {
			Contact contact = (Contact) obj;
			ans.add(contact.getFirstName() + " " + contact.getLastName() + " - " + contact.getPhone());
		}

		return ans;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return CommonUtil.generateContext(request.getSession().getId());
	}
	
	

}
