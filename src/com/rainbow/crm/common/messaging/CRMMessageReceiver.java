package com.rainbow.crm.common.messaging;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.wishlist.service.IWishListService;

public class CRMMessageReceiver implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Logwriter.INSTANCE.debug("message=" + message);
		ObjectMessage objMsg = (ObjectMessage)message ;
		try {
			if (objMsg.getObject() instanceof InventoryUpdateObject) {
				InventoryUpdateObject inventoryObject = (InventoryUpdateObject)objMsg.getObject() ; 
				IInventoryService service = (IInventoryService)SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
				if (inventoryObject.getDivision() != null )
					service.updateInventory(inventoryObject);
				if (inventoryObject.isAddition()) {
					IWishListService wishService = (IWishListService)SpringObjectFactory.INSTANCE.getInstance("IWishListService");
					if (inventoryObject.getDivision() != null )
						wishService.generateSalesLead(inventoryObject, "AVLBLTY");
					else
						wishService.generateSalesLead(inventoryObject, "LOWPRICE");
				}
			}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		
	}
	
	

}
