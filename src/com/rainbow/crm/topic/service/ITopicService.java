package com.rainbow.crm.topic.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.topic.model.Topic;

public interface ITopicService extends ITransactionService{

	public List<Topic> getPortfoliosforExpiry(Date date) ;
	
	public List<Topic> getUsersforItem (Sku sku, int divisionId, Date date) ;


  
}
