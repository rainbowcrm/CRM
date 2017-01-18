package com.rainbow.crm.salesportfolio.dao;

import java.sql.Timestamp;
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

	
	
	public List<SalesPortfolio> getPortfoliosforExpiry(Date date) {
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPortfolio where endDate < :endDate    and expired = false  " ) ;
		query.setParameter("endDate", new Timestamp(date.getTime() ));
		List<SalesPortfolio> lst = query.list();
		closeSession(session, false);
		return lst;
	}

	public List<Object> getPortfoliosforsku(int itemId, int productId, int brandId, int categoryId) {
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPortfolio parent  left join   SalesPortfolioLine as line with parent.id = line.salesPortfolioDoc.id  where (  " +
		     " ( line.portfolioType.code = 'SPFITEM' and line.portfolioKey = :itemId ) or " +
			"  ( line.portfolioType.code = 'SPFPROD' and line.portfolioKey = :productId ) or " +
			" ( line.portfolioType.code = 'SPFBRAND' and line.portfolioKey = :brandId ) or " +
			"  ( line.portfolioType.code = 'SPFCATG' and line.portfolioKey = :categoryId )  " +
				" )" );
		query.setParameter("itemId", String.valueOf(itemId));
		query.setParameter("productId", String.valueOf(productId));
		query.setParameter("brandId", String.valueOf(brandId));
		query.setParameter("categoryId", String.valueOf(categoryId));
		List<Object> lst = query.list(); 
		closeSession(session, false);
		return lst;
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
