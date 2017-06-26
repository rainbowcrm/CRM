import { BaseSearchRequest } from '../../../providers/';

export class AlertSearchRequest extends BaseSearchRequest{
}

export class AcknowledgeAlertRequest extends BaseSearchRequest{
    submitAction: string;
}

export class Alert{
  Division: Division;
  Type: Type;
  Status: Type;
  RaisedDate: String;
  ActionDate: String;
  AcknowDate: String;
  RaisedBy: String;
  Data: String;
  Url: String;
  Owner: Owner;
  FilterName: string;
  Id: string;
}

export class Owner{
    UserId: string;
}

export class Division{
    Name: string;
    Code: string;
}

export class Type{
   Type: string;
   Code: string;
   Description: string;
   IsDefault: string;
}

