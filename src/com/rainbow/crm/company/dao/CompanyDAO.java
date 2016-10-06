package com.rainbow.crm.company.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.techtrade.rads.framework.utils.Utils;


public class CompanyDAO extends SpringHibernateDAO{
	
	public static CompanyDAO  INSTANCE  = new CompanyDAO();

	@Override
	public Object getById(Object PK) {
		int companyId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Company.class, companyId);
		closeSession(session,false);
		return obj;
	}
	
	public Company findByCode(String code) {
		Company company = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Company where code = :code  " ) ;
		query.setParameter("code", code);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			company = (Company) lst.get(0) ;
		closeSession(session, false);
		return company;
	}
	
	public Company findByName(String name) {
		Company company = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Company where name = :name  " ) ;
		query.setParameter("name", name);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			company = (Company) lst.get(0) ;
		closeSession(session, false);
		return company;
	}

	
	
	
	
}
