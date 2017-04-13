import { BaseSearchRequest } from '../../../providers/';
export class Customer{
    Description:string;
    FullName:string;
    Phone:string;
    FirstName:string;
    LastName:string;
    Email:string;
    Address2:string;
    Address1:string;
    ZipCode:string;
    City:string;
    CreditLimit:string;
    Landmark:string;
    AlternatePhone:string;
    Id: string;
    Deleted: string;
}

export class CustomerSearchRequest extends BaseSearchRequest{
    
}

export class CustomerDeleteRequest extends BaseSearchRequest{
}

export class CustomerSearchResponse{
    result: string;
    dataObject:Array<Customer>;
}

export class CustomerAddRequest{
    fixedAction:string;
    pageID:string;
    currentmode:string;
    dataObject:Customer;
}

export class CustomerAddResponse{
    result: string;
    dataObject:Customer;
}