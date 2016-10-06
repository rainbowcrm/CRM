package com.rainbow.framework.setup.model;

import com.rainbow.crm.abstratcs.model.CRMModelObject;

public class Metadata  extends CRMModelObject{

	String objectName;
	String className;
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
