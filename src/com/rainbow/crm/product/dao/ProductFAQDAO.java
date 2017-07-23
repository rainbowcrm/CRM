package com.rainbow.crm.product.dao;

import java.util.List;

import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ProductFAQDAO extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int product = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Product.class, product);
		closeSession(session,false);
		return obj;
	}

	
	public void batchUpdate(List<? extends CRMModelObject> objects)  throws DatabaseException{
		Session session = openSession(true);
		boolean success = false; 
		try {
			for (ModelObject object : objects) {
				session.saveOrUpdate(object);
			}
			session.flush();
			success = true;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.PERSISTENCE_ERROR);
		}finally {
			closeSession(session,success);
		}
	}

	
	
}
