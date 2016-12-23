package com.rainbow.crm.custcategory.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.techtrade.rads.framework.utils.Utils;

public class CustCategoryDAO extends SpringHibernateDAO {

	@Override
	public Object getById(Object PK) {
		int custCategoryID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(CustCategory.class, custCategoryID);
		if (obj != null) {
			CustCategory custCategory =(CustCategory) obj;
		}
		closeSession(session,false);
		return obj;
	}
	

	

}
