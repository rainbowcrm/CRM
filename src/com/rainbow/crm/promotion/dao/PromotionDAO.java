package com.rainbow.crm.promotion.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.customer.model.Customer;
import com.techtrade.rads.framework.utils.Utils;

public class PromotionDAO extends SpringHibernateDAO {

	@Override
	public Object getById(Object PK) {
		int promotionID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Promotion.class, promotionID);
		
		closeSession(session,false);
		return obj;
	}
	
	

}
