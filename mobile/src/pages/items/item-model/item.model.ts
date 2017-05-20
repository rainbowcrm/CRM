import { BaseSearchRequest } from '../../../providers/';
export class Product{
    Name:string
}



export class Item{
    ItemClass: string;
    Description: string;
    RetailPrice: number;
    WholeSalePrice: number;
    ItemClassDesc: string;
    Size: number;
    Color: string;
    Product: Product;
    BreakEvenPrice: number;
    Specification: string;
    PurchasePrice: number;
    Code: string;
    Name: string;
    PromotionPrice: number;
    UomId: string;
    MaxDiscount: string;
    ObjectVersion: number;
    Uom: string;
    OnPromotion: string;
    Barcode: string;
    Manufacturer: string;
    Id: string;
    Deleted: string;
    MaxPrice: number;
    Item:ItemBrand;
    Image1URL: string;
    Image2URL: string;
    Image3URL: string;
    Inventory: string;

}

export class ItemBrand{
    Name: string;
}

export class ItemSearchRequest extends BaseSearchRequest{
}

export class ItemSearchResponse{
    result: string;
    dataObject:Array<Item>;
    fetchedRecords: number;   
    availableRecords: number;
}

export class ItemSearchFilter{
    field: string;
    operator: string;
    value: string;
}
