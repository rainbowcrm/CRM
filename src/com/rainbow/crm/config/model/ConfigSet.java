package com.rainbow.crm.config.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ConfigSet extends ModelObject{

	int company ;
	
	Map<String, List<ConfigLine>> configMap = new HashMap<String, List<ConfigLine>>();

	public Map<String, List<ConfigLine>> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, List<ConfigLine>> configMap) {
		this.configMap = configMap;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}
	
	public boolean isNull () {
		return Utils.isNullMap(configMap); 
	}

}
