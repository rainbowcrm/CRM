package com.rainbow.crm.territory.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.territory.model.Territory;
import com.techtrade.rads.framework.utils.Utils;

public class TerritoryDAO  extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int territoryId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Territory.class, territoryId);
		closeSession(session, false);
		return obj;
	}


	
	
	

	
	
	
}
