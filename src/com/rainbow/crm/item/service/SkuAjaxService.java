package com.rainbow.crm.item.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

public class SkuAjaxService implements IAjaxLookupService{

	@Override
	public String lookupValues(Map<String, String> searchFields,
			IRadsContext ctx) {
		CRMContext context = (CRMContext)ctx;
		String code  = searchFields.get("Code");
		String barCode = searchFields.get("Barcode");
		String itName = searchFields.get("Name");
		ISkuService service = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		Sku item = null;
		if(!Utils.isNull(itName)){
			item =service.getByName(context.getLoggedinCompany(), itName);
		}else if(!Utils.isNull(code)){
			item =service.getByCode(context.getLoggedinCompany(), code);
		}else if(!Utils.isNull(barCode)){
			item =service.getByBarCode(context.getLoggedinCompany(), barCode);
		}
		try {
		if (item != null) {
			JSONObject json = new JSONObject();
			json.put("Code", item.getCode());
			json.put("Barcode", item.getBarcode());
			json.put("Name", item.getName());
			json.put("Color", item.getColor());
			json.put("Size", item.getSize());
			json.put("Specification", item.getSpecification());
			json.put("PurchasePrice", item.getPurchasePrice());
			json.put("RetailPrice", item.getRetailPrice());
			return json.toString();
		}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		
		return null;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		return LoginSQLs.loggedInUser(request.getSession().getId());
	}
	

}
