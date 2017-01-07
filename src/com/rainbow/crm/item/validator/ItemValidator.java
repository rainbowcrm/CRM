package com.rainbow.crm.item.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.validator.DivisionErrorCodes;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ItemValidator extends CRMValidator{

	Sku item =null;
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISkuService  service = (ISkuService) SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		Sku  exist = (Sku)service.getByCode(item.getCompany().getId(), item.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Code"))) ;
		}
		exist = (Sku)service.getByBarCode(item.getCompany().getId(), item.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Barcode"))) ;
		}
		exist = (Sku)service.getByName(item.getCompany().getId(), item.getName());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Name"))) ;
		}
		System.out.println(item.toJSON());
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISkuService  service = (ISkuService) SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		Sku  exist = (Sku)service.getByCode(item.getCompany().getId(), item.getCode());
		if(exist != null && exist.getId() != item.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Code"))) ;
		}
		exist = (Sku)service.getByBarCode(item.getCompany().getId(), item.getCode());
		if(exist != null && exist.getId() != item.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Barcode"))) ;
		}
		exist = (Sku)service.getByName(item.getCompany().getId(), item.getName());
		if(exist != null && exist.getId() != item.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Name"))) ;
		}
		System.out.println("ite json=" + item.toJSON());
	}
	
	protected void checkforErrors(ModelObject object) {
		item = (Sku) object;
		if(item.getCode() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item_Code"))) ;
		}
		if(item.getBarcode() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Barcode"))) ;
		}
		if(item.getName() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Name"))) ;
		}
		if(item.getProduct() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Product"))) ;
		}else {
			IProductService service =(IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
			Product product = service.getByName(item.getCompany().getId(),item.getProduct().getName());
			item.setProduct(product);
		}
		if(item.getUomId() <= 0) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "UOM"))) ;
		}
		if (item.getPurchasePrice() != null && item.getPurchasePrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Purchase_Price"))) ;
		}
		if (item.getWholeSalePrice() != null && item.getWholeSalePrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Wholesale_Price"))) ;
		}
		if (item.getBreakEvenPrice() != null && item.getBreakEvenPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "BreakEven_Price"))) ;
		}
		if (item.getRetailPrice() != null && item.getRetailPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Retail_Price"))) ;
		}
		if (item.getMaxPrice() != null && item.getMaxPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Max_Price"))) ;
		}
		if (item.getPromotionPrice() != null && item.getPromotionPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Promotion_Price"))) ;
		}
		if (item.getMaxPrice() != null && item.getMaxPrice() >=0 ){
			double maxPrice = item.getMaxPrice().doubleValue() ;
			if (item.getPromotionPrice() != null && item.getPromotionPrice() >0)
				checkforMaxPriceError(maxPrice, "Promotion_Price", item.getPromotionPrice());
			if (item.getRetailPrice() != null && item.getRetailPrice() >0)
				checkforMaxPriceError(maxPrice, "Retail_Price", item.getRetailPrice());
			if (item.getBreakEvenPrice() != null && item.getBreakEvenPrice() >0)
				checkforMaxPriceError(maxPrice, "BreakEven_Price", item.getBreakEvenPrice());
			if (item.getWholeSalePrice() != null && item.getWholeSalePrice() >0)
				checkforMaxPriceError(maxPrice, "Wholesale_Price", item.getWholeSalePrice());
			if (item.getPurchasePrice() != null && item.getPurchasePrice() >0)
				checkforMaxPriceError(maxPrice, "Purchase_Price", item.getPurchasePrice());
		}
		
	}
	
	private void checkforMaxPriceError (double maxPrice, String prop , double compPrice) {
		if (compPrice > maxPrice) {
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,
					externalize.externalize(context, "Max_Price"),externalize.externalize(context, prop))) ;
		}
	}
	public ItemValidator(CRMContext context) {
		super(context);
	}
	public ItemValidator(){
		
	}
	
	

}
