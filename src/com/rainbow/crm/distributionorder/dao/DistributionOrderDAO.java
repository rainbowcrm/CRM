package com.rainbow.crm.distributionorder.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.techtrade.rads.framework.utils.Utils;

public class DistributionOrderDAO  extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int distributionOrderId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(DistributionOrder.class, distributionOrderId);
		closeSession(session, false);
		return obj;
	}


	
	

	
	
	
}
