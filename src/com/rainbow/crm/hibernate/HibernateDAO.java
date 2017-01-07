package com.rainbow.crm.hibernate;

import java.util.List;

import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


































import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.filter.model.CRMFilterDetails;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.purchase.model.Purchase;
import com.rainbow.crm.purchase.model.PurchaseLine;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.uom.model.UOM;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.wishlist.model.WishList;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public  abstract class HibernateDAO  extends ORMDAO{
	
    private static SessionFactory sessionFactory = null;

    
    protected void closeSession(Session session,boolean commitTransaction) {
    	if (commitTransaction && session.getTransaction() !=null ) {
    		session.getTransaction().commit();
    	} else if (!commitTransaction && session.getTransaction() !=null ) {
    		session.getTransaction().rollback();
    	} 
    	session.close();
    }
     
    protected Session openSession(boolean newTransaction) {
    	Session session = sessionFactory.openSession() ;
    	if (newTransaction) {
    		session.beginTransaction();
    	}
    	return session;
    }

    protected Session getCurrentSession() {
    	return sessionFactory.getCurrentSession();
    }
    
    public static void instantiate(ServletContext ctx) {
    	if (sessionFactory == null) {
    		String hibernatePath = ctx.getInitParameter("hibernateConfig");
    		Configuration  configuration = new Configuration().configure( );
    		configuration.addClass(FiniteValue.class).addResource("com/rainbow/crm/common/finitevalue/FiniteValue.hbm.xml");
    		configuration.addClass(User.class).addResource("com/rainbow/crm/user/model/User.hbm.xml");
    		configuration.addClass(Company.class).addResource("com/rainbow/crm/company/model/Company.hbm.xml");
    		configuration.addClass(CRMFilter.class).addResource("com/rainbow/crm/filter/model/CRMFilter.hbm.xml");
    		configuration.addClass(CRMFilterDetails.class).addResource("com/rainbow/crm/filter/model/CRMFilterDetails.hbm.xml");
    		configuration.addClass(Division.class).addResource("com/rainbow/crm/division/model/Division.hbm.xml");
    		configuration.addClass(Category.class).addResource("com/rainbow/crm/category/model/Category.hbm.xml");
    		configuration.addClass(Product.class).addResource("com/rainbow/crm/product/model/Product.hbm.xml");
    		configuration.addClass(UOM.class).addResource("com/rainbow/crm/uom/model/UOM.hbm.xml");
    		configuration.addClass(Sku.class).addResource("com/rainbow/crm/item/model/Sku.hbm.xml");
    		configuration.addClass(Vendor.class).addResource("com/rainbow/crm/vendor/model/Vendor.hbm.xml");
    		configuration.addClass(Carrier.class).addResource("com/rainbow/crm/carrier/model/Carrier.hbm.xml");
    		configuration.addClass(Customer.class).addResource("com/rainbow/crm/customer/model/Customer.hbm.xml");
    		configuration.addClass(Address.class).addResource("com/rainbow/crm/address/model/Address.hbm.xml");
    		configuration.addClass(Inventory.class).addResource("com/rainbow/crm/inventory/model/Inventory.hbm.xml");
    		configuration.addClass(Purchase.class).addResource("com/rainbow/crm/purchase/model/Purchase.hbm.xml");
    		configuration.addClass(PurchaseLine.class).addResource("com/rainbow/crm/purchase/model/PurchaseLine.hbm.xml");
    		configuration.addClass(SalesPeriod.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriod.hbm.xml");
    		configuration.addClass(SalesPeriodLine.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriodLine.hbm.xml");
    		configuration.addClass(Sales.class).addResource("com/rainbow/crm/sales/model/Sales.hbm.xml");
    		configuration.addClass(SalesLine.class).addResource("com/rainbow/crm/sales/model/SalesLine.hbm.xml");
    		configuration.addClass(WishList.class).addResource("com/rainbow/crm/wishlist/model/WishList.hbm.xml");
    		configuration.addClass(WishListLine.class).addResource("com/rainbow/crm/wishlist/model/WishListLine.hbm.xml");
    		configuration.addClass(Sales.class).addResource("com/rainbow/crm/saleslead/model/SalesLead.hbm.xml");
    		configuration.addClass(SalesLine.class).addResource("com/rainbow/crm/saleslead/model/SalesLeadLine.hbm.xml");
    		configuration.addClass(Sales.class).addResource("com/rainbow/crm/distributionorder/model/DistributionOrder.hbm.xml");
    		configuration.addClass(SalesLine.class).addResource("com/rainbow/crm/distributionorder/model/DistributionOrderLine.hbm.xml");
    		configuration.addClass(Followup.class).addResource("com/rainbow/crm/followup/model/Followup.hbm.xml");
    		configuration.addClass(CustCategory.class).addResource("com/rainbow/crm/custcategory/model/CustCategory.hbm.xml");
    		configuration.addClass(Contact.class).addResource("com/rainbow/crm/contact/model/Contact.hbm.xml");
    		configuration.addClass(Alert.class).addResource("com/rainbow/crm/alert/model/Alert.hbm.xml");
    		sessionFactory = configuration.configure().buildSessionFactory();
    		
    		//sessionFactory.getAllClassMetadata().put(key, value)
    	}
    }
    
    	
}
