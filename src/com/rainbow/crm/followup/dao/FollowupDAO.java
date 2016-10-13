package com.rainbow.crm.followup.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.followup.model.Followup;
import com.techtrade.rads.framework.utils.Utils;

public class FollowupDAO extends SpringHibernateDAO {

	@Override
	public Object getById(Object PK) {
		int followupID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Followup.class, followupID);
		if (obj != null) {
			Followup followup =(Followup) obj;
			followup.setFullName(followup.getFirstName() + " " + followup.getLastName());
		}
		closeSession(session,false);
		return obj;
	}
	
	public Followup findByEmail(int company, String email) {
		Followup followup = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Followup where email = :email and company.id =:company  " ) ;
		query.setParameter("email", email);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			followup = (Followup) lst.get(0) ;
			followup.setFullName(followup.getFirstName() + " " + followup.getLastName());
		}
		closeSession(session, false);
		return followup;
	}
	
	
	public Followup findByPhone(int company, String phone) {
		Followup followup = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Followup where phone = :phone and company.id =:company  " ) ;
		query.setParameter("phone", phone);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			followup = (Followup) lst.get(0) ;
			followup.setFullName(followup.getFirstName() + " " + followup.getLastName());
		}
		closeSession(session, false);
		return followup;
	}

}
