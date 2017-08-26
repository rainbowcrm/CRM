import { BaseSearchRequest } from '../../../providers/';

export class SalesLeadSearchRequest extends BaseSearchRequest{
    contextParameters:ContextParameters;
}

export class ContextParameters{
    workableleads: string;
}

export class SalesLeadEmailRequest extends BaseSearchRequest{
    submitAction: string;
}

export class SalesLeadSearchResponse extends BaseSearchRequest{
    result: string;
    dataObject:Array<SalesLeads>;
    fetchedRecords: number;   
    availableRecords: number;
}

export class SalesLeadSearchFilter{
    field: string;
    operator: string;
    value: string;
}

export class SalesLeads{
   Alerted: string;
   Comments: string;
   Customer: Customer;
   Deleted: string;
   Division: Division;
   DocNumber: string;
   Id: string;
   MgrReasonCode: string;
   ObjectVersion: string;
   RefDate: string;
   RefNo: string;
   Referall: string;
   ReleasedDate: string;
   Sales: string;
   SalesAssReasonCode: string;
   SalesLeadLines:Array<SalesLeadLine>;
   SalesWon: string;
   Territory: string;
   Status: Status;
   Voided: string;
   FilterName: string;
}

export class Status{
    Type: string;
    Description: string;
    Default: string;
    Code: string;
}

export class Customer{
    Phone: string;
    FirstName: string;
    LastName: string;
    FullName: string;
}

export class Division{
    Name: string;
    Code: string;
}

export class SalesLeadLine{
   Comments: string;
   Deleted: string;
   Division: Division;
   DocNumber: string;
   Id: string;
   Image1URL: string;
   Image2URL: string;
   Image3URL: string;
   LineNumber: string;
   NegotiatedPrice: string;
   ObjectVersion: string;
   Price: string;
   Qty: string;
   ReasonCode: string;
   Sales: string;
   SalesWon: string;
   Sku: ItemSku;
   Voided: string;
}

export class ItemSku{
    Name: string;
}





