package com.rainbow.crm.item.controller;

import java.util.Map;

import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.item.service.IItemService;

public class ItemController extends CRMCRUDController{

	public IBusinessService getService() {
		IItemService serv = (IItemService) SpringObjectFactory.INSTANCE.getInstance("IItemService");
		return serv;
	}
	
	
	public Map <String, String > getUOMs() {
		Map<String, String> ans = GeneralSQLs.getDefaultUOMs();
		return ans;
	}
}
