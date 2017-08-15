import { BaseSearchRequest } from '../../../providers/';



export class Division{
    Name: String;
    Code: String;
}

export class NewEnquiry{
  user: String;
  Enquiry: Enquiry;
}

export class Enquiry{
  Comments: String;
  EnquiryType: Code;
  EnquirySource: Code;
  Enquiry: String;
  Email: String;
  EnqDate: String;
  Phone: String;
  Territory: String;
  FirstName: String;
  LastName: String;
  SalesAssociate: String;
  Address1: String;
  Address2: String;
  Zipcode: String;
  City: String;
}

export class Code{
  Code: String;
}
