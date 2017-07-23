package com.rainbow.crm.product.dao;

import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.product.model.Product;

public class ProductFAQDAO extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int product = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Product.class, product);
		closeSession(session,false);
		return obj;
	}

	
	
	
}
