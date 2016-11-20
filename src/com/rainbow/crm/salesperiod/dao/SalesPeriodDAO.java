package com.rainbow.crm.salesperiod.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.filter.model.CRMFilter;
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

	public List<SalesPeriod> getStartingSalesPeriodsforAlerts(Date startDt )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPeriod where fromDate = :fromDate    and startAlerted = false and voided= false " ) ;
		query.setParameter("fromDate", startDt);
		List<SalesPeriod> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public List<SalesPeriod> getEndingSalesPeriodsforAlerts(Date toDate )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPeriod where toDate = :toDate    and endAlerted = false and voided= false " ) ;
		query.setParameter("toDate", toDate);
		List<SalesPeriod> lst = query.list();
		closeSession(session, false);
		return lst;
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
