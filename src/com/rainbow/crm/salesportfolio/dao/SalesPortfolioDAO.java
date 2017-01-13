package com.rainbow.crm.salesportfolio.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;

public class SalesPortfolioDAO  extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int salesPortfolioId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(SalesPortfolio.class, salesPortfolioId);
		closeSession(session, false);
		return obj;
	}

	
	
	
	/*@Override
	public void create(CRMModelObject object) {
		SalesPortfolio salesPortfolio = (SalesPortfolio) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(salesPortfolio);
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
