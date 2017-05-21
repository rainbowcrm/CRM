import { BaseSearchRequest } from '../../providers/';


export class RegTokenRequest extends BaseSearchRequest{
    submitAction: string;
    dataObject: Token;
}

export class Token{
   username:string;
   AuthToken: string;
   mobileNotificationId: string;
}


