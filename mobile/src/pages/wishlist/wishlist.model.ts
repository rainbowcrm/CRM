import { BaseSearchRequest } from '../../providers/';


export class WishListRequest extends BaseSearchRequest{
}

export class ReasonCodeItem{
    reasonCode: string;
    desiredDate: string;
    desiredPrice: string;
    comments: string;
}


