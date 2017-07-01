package com.rainbow.crm.sales.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.techtrade.rads.framework.utils.Utils;

public class SalesDAO  extends SpringHibernateDAO{

	@Override
	public Object getById(Object PK) {
		int salesId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Sales.class, salesId);
		closeSession(session, false);
		return obj;
	}

	public List<Sales> getNonAlertedSalesFeedBack(int company , Date startDate)
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from Sales where company.id = :company    and salesDate <= :startDate and  " +
		 " feedBackAlerted =false and feedBackCaptured =false and voided= false " ) ;
		query.setParameter("company", company);
		query.setParameter("startDate", startDate);
		List<Sales> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public Sales getByBillNumberandDivision(Division division, String billNumber)
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from Sales where division.id = :division    and billNumber = :billNumber and voided= false " ) ;
		query.setParameter("division", division.getId());
		query.setParameter("billNumber", billNumber);
		List<Sales> lst = query.list();
		closeSession(session, false);
		if(!Utils.isNullList(lst))
			return lst.stream().findFirst().get();
		else
			return null;
		
	}


	/*public int getTotalQtySold (Item item , Date from , Date To) {
		Session session = openSession(false) ;
    	try  {
    	String queryString = " Select sum(qty) from SalesLineItem line ,Sales sales where line= "  ;
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
		Sales sales = (Sales) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(sales);
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
