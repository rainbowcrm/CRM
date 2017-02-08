package com.rainbow.framework.query.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.framework.query.model.QueryReport;
import com.techtrade.rads.framework.utils.Utils;

public class QueryDAO  extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List getQueryRecord(String queryString, int company, Date fromDate, Date toDate)
	{
		Session session = openSession(false);
		try {
		
		Query query = session.createQuery( queryString  ) ;
		query.setParameter("company", company);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		List lst = query.list();
		return lst;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			closeSession(session,false);
		}
			
		return  null;
	}
	

	

}
