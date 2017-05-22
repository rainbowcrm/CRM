import { BaseSearchRequest } from '../../../providers/';
export class Contact{
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
    Deleted:string;
    ObjectVersion:number;
    ContactType: ContactType;
    Id: string;
    IdentifierName: string;
}

export class ContactType{
    Code: string;
    Type: string;
    Description: string;
    Default: string;
}


export class ContactAddRequest{
    fixedAction:string;
    pageID:string;
    currentmode:string;
    dataObject:Contact;
}

export class ContactAddResponse{
    result: string;
    dataObject:Contact;
}

export class ContactSearchRequest extends BaseSearchRequest{
}

export class ContactDeleteRequest extends BaseSearchRequest{
}

export class ContactSearchResponse{
    result: string;
    dataObject:Array<Contact>;
    fetchedRecords: number;   
    availableRecords: number;
}