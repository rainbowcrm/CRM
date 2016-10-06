package com.rainbow.crm.inventory.validator;

import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.service.ICategoryService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.service.IInventoryService;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.service.IItemService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class InventoryValidator extends CRMValidator {
	
	Inventory inventory;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IInventoryService service =(IInventoryService)SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
		if (inventory.getItem() != null && inventory.getDivision() !=null) {
			Inventory exist = (Inventory)service.getByItemandDivision(inventory.getItem(), inventory.getDivision()) ;
			if(exist != null) {
					errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Division_Combination"))) ;
			}
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IInventoryService service =(IInventoryService)SpringObjectFactory.INSTANCE.getInstance("IInventoryService");
		if (inventory.getItem() != null && inventory.getDivision() !=null) {
			Inventory exist = (Inventory)service.getByItemandDivision(inventory.getItem(), inventory.getDivision()) ;
			if(exist != null && exist.getId() != inventory.getId()) {
					errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Division_Combination"))) ;
			}
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		inventory =  (Inventory) object;
		if (inventory.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (inventory.getDivision() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Division"))) ;
		}
		if (inventory.getItem() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
		}else {
			IDivisionService service = (IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division =  null ;
			if (inventory.getDivision().getId() > 0) {
				division = (Division)service.getById(inventory.getDivision().getPK());
			}else if (!Utils.isNullString(inventory.getDivision().getName()) ) {
				division = service.getByName(context.getLoggedinCompany(),inventory.getDivision().getName()) ;
			}
			if (division == null ) {
				errors.add(getErrorforCode(CommonErrorCodes.VALUE_NOT_FOUND,externalize.externalize(context, "Division"))) ;
			}else
				inventory.setDivision(division);
			IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
			Item item = itemService.getByCode(context.getLoggedinCompany(),inventory.getItem().getCode());
			if (item  == null) {
				errors.add(getErrorforCode(CommonErrorCodes.VALUE_NOT_FOUND,externalize.externalize(context, "Item"))) ;
			}else
				inventory.setItem(item);
			
		}
		/**
		 * Place holder  for validation if purchase or sale is made..
		 */
		inventory.setCurrentQty(inventory.getOpQty());
	}

	public InventoryValidator(CRMContext context) {
		super(context);
	}
	public InventoryValidator(){
		
	}


}
