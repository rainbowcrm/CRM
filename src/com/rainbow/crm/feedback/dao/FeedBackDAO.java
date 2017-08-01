package com.rainbow.crm.feedback.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.feedback.model.FeedBack;
import com.rainbow.crm.feedback.model.FeedBackLine;
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


	
	public FeedBack getBySalesBill( String billNumber , int company )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from FeedBack where company.id = :company    and sales.billNumber = :billNumber and deleted= false " ) ;
		query.setParameter("company", company);
		query.setParameter("billNumber", billNumber);
		List<FeedBack> lst = query.list();
		closeSession(session, false);
		if(!Utils.isNullList(lst))
			return lst.stream().findFirst().get();
		else
			return null;
		
	}


	public List<FeedBackLine> getByItem( String item , int company )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from FeedBackLine where sku.item.id = :item    and sales.billNumber = :billNumber and deleted= false " ) ;
		query.setParameter("item", item);
		List<FeedBackLine> lst = query.list();
		closeSession(session, false);
		return lst;
		
	}
	
	
	
}
