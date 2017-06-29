package com.rainbow.crm.inventory.model;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;

public class InventoryDelta extends CRMModelObject{

	List<InventoryDeltaLine> lines ;
	
	CRMContext context;

	public List<InventoryDeltaLine> getLines() {
		return lines;
	}

	public void setLines(List<InventoryDeltaLine> lines) {
		this.lines = lines;
	}
	
	public void addLine(InventoryDeltaLine line) {
		if(lines == null)
			lines = new ArrayList<InventoryDeltaLine>();
		lines.add(line);
	}

	public CRMContext getContext() {
		return context;
	}

	public void setContext(CRMContext context) {
		this.context = context;
	}
	
	
	
}
