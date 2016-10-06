package com.rainbow.crm.item.service;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.item.model.Item;

public interface IItemService extends IBusinessService{

	public Item getByCode(int company, String code) ;
	public Item getByBarCode(int company, String barcode) ;
	public Item getByName(int company, String name) ;
	

	

}
