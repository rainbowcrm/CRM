export class Product{
    Name:string
}

export class Item{
    ItemClass: string;
    Description: string;
    RetailPrice: number;
    WholeSalePrice: number;
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
    MaxPrice: number
}

export class ItemSearchRequest{
    fixedAction:string;
    pageID:string;
    currentmode:string;
    filter:Array<any>;
}

export class ItemSearchResponse{
    result: string;
    dataObject:Array<Item>;
}
