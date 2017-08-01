package com.rainbow.crm.profile.controller;

import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.profile.model.ItemProfile;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ItemProfileController extends CRMGeneralController {

	@Override
	public PageResult submit(ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult read(ModelObject object) {
		ItemProfile profile = (ItemProfile) object; 
		if(profile != null ) {
			
		}
		return super.read(object);
	}

	
	
}
