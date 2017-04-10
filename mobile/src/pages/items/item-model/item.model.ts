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
    Brand:ItemBrand;
}

export class ItemBrand{
    Name: string;
}

export class ItemSearchRequest extends BaseSearchRequest{
}

export class ItemSearchResponse{
    result: string;
    dataObject:Array<Item>;
}

export class ItemSearchFilter{
    field: string;
    operator: string;
    value: string;
}
