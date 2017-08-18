import { BaseSearchRequest } from '../../../providers/';

export class Product{
  ObjectVersion: number;
  Description: string;
  Category: Category;
  Id: number;
  Deleted: boolean;
  Name: string;
}

export class ProductDetails{
  ObjectVersion: number;
  Id: number;
  Deleted: boolean;
  Product: Product;
  ProductAttributes: Array<ProductAttributes>;
  ProductFAQs: Array<ProductFAQs>;
  ProductPriceRanges: Array<ProductPriceRanges>;
}

export class ProductAttributes{
    Attribute: string;
    Comments: string;
    Deleted: boolean;
    ObjectVersion: number;
    Id: number;
    Product: Category;
    ValueType: ValueType;
}

export class ValueType{
    Code: string;
    Default: boolean;
    Description: string;
    Type: string;
}

export class ProductFAQs{
  Answer: string;
  Author: Author;
  Comments: string;
  Deleted: boolean;
  ObjectVersion: number;
  Id: number;
  LineNumber: number;
  Product: Category;
  Question: string
}

export class Author{
    UserId: string;
}

export class ProductPriceRanges{
  Comments: string;
  Deleted: boolean;
  ObjectVersion: number;
  Id: number;
  ItemClass: ItemClass;
  MaxPrice: number;
  MinPrice: number;
  Product: Category;
}

export class ItemClass{
    Code: string;
}

export class Category{
    Name: string;
}



export class GetProductsRequest extends BaseSearchRequest{

}

export class GetProductDetailsRequest extends BaseSearchRequest{

}
