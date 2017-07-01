package com.rainbow.crm.feedback.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.feedback.model.FeedBack;
import com.techtrade.rads.framework.utils.Utils;

public class FeedBackDAO  extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int feedBackId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(FeedBack.class, feedBackId);
		closeSession(session, false);
		return obj;
	}


	/*public int getTotalQtySold (Item item , Date from , Date To) {
		Session session = openSession(false) ;
    	try  {
    	String queryString = " Select sum(qty) from FeedBackLineItem line ,FeedBack feedBack where line= "  ;
    	Query  query = session.createQuery(queryString);
    	List lst = query.list();
    	if(!Utils.isNull(lst)) {
    	  Object obj = lst.get(0);
    	  if (obj!=null && obj instanceof Integer) {
    		  return(((Integer)obj).intValue() +1 );
    	  }
    	}
    		return 1;
    	}finally{
    		session.close();
    	}
	}*/
	
	/*@Override
	public void create(CRMModelObject object) {
		FeedBack feedBack = (FeedBack) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(feedBack);
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
