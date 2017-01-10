package com.rainbow.crm.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMContext;
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
	
	public Map <String, String > getAllBrands() {
		Map <String, String >  map = new HashMap<String,String>();
		IBrandService service = (IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
		List<Brand> brandList= service.getAllBrands(((CRMContext)getContext()).getLoggedinCompany());
		brandList.forEach( brand ->  { 
			map.put(String.valueOf( brand.getId()), brand.getName());
		});
		return map;
	}
}
