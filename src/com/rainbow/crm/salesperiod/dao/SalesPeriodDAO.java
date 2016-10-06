package com.rainbow.crm.salesperiod.dao;

import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;

public class SalesPeriodDAO  extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int salesPeriodId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(SalesPeriod.class, salesPeriodId);
		closeSession(session, false);
		return obj;
	}

	/*@Override
	public void create(CRMModelObject object) {
		SalesPeriod salesPeriod = (SalesPeriod) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(salesPeriod);
			session.flush();
			success = true; 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.DIRTY_READ_ERROR);
		}finally {
			closeSession(session,success);
		}
	}*/
	
	

	
	
	
}
