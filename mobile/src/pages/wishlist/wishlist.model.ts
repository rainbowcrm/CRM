import { BaseSearchRequest } from '../../providers/';


export class WishListRequest extends BaseSearchRequest{
}

export class ReasonCodeItem{
    reasonCode: string;
    desiredDate: string;
    desiredPrice: string;
    comments: string;
    quantity: string;
}

export class NewWishListRequest extends BaseSearchRequest{
   dataObject: WishList;
}

export class WishList{
   Division: Division;
   WishListDate: string;
   Customer: CustomerMetaData;
   WishListLines: Array<WishlistLineItem>;
}

export class Division{
    Name: string;
    Code: string;
}

export class CustomerMetaData{
    Phone: string;
}

export class WishlistLineItem{
   LineNumber: string;
   Comments: string;
   ReasonCode: string;
   DesiredDate: string;
   DesiredPrice: string;
   SalesLeadGenerated: string;
   Sku: Sku;
   Qty: string;
}

export class Sku{
  Name: string;
  imageUrl: string;
}



