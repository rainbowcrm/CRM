package com.rainbow.crm.profile.controller;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.ItemUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.profile.model.ItemProfile;
import com.rainbow.crm.profile.service.IItemProfileService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ItemProfileController extends CRMGeneralController {

	@Override
	public PageResult submit(ModelObject object) {
		// TODO Auto-generated method stub
		return new PageResult();
	}

	@Override
	public PageResult read(ModelObject object) {
		ItemProfile profile = (ItemProfile) object; 
		if(profile != null && profile.getItem() != null  ) {
			Item item = ItemUtil.getItem((CRMContext) getContext(), profile.getItem());
			profile.setItem(item);
			IItemProfileService service = (IItemProfileService)SpringObjectFactory.INSTANCE.getInstance("IItemProfileService");
			service.getItemProfile(profile.getItem(), (CRMContext) getContext());
		}
		return super.read(object);
	}

	
	
}
