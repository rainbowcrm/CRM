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
}

export class CustomerSearchRequest{
    fixedAction:string;
    pageID:string;
    currentmode:string;
    filter:Array<any>;
    pageNumber: number;
    dataObject: Customer;
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