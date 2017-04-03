package com.rainbow.crm.item.controller;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.SkuComplete;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class SkuCompleteController  extends SkuController{
	
	
	@Override
	public void read() {
		try {
		ModelObject thisObject = getService().getByBusinessKey((CRMModelObject)object, (CRMContext)getContext());
		SkuComplete itemComplete = new SkuComplete((Sku) thisObject);
		List<String > imageURLs = ItemImageSQL.getAllItemImages(itemComplete.getId());
		if(!Utils.isNullList(imageURLs)) {
			String path = ConfigurationManager.getConfig(
					ConfigurationManager.IMAGE_SERVER_URL, (CRMContext)getContext());
			itemComplete.setImage1URL(path + "/" + imageURLs.get(0).toString());
			if(imageURLs.size() > 1) 
				itemComplete.setImage2URL(path + "/" + imageURLs.get(1).toString());
			if(imageURLs.size() > 2) 
				itemComplete.setImage3URL(path + "/" + imageURLs.get(2).toString());
		}
		setObject(itemComplete);
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
	}
}
