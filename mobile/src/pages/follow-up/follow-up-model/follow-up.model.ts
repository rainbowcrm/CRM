import { BaseSearchRequest } from '../../../providers/';

export class FollowUp{
    Division: Division;
    Result: Result;
    Lead: Lead;
    ResultReason: ResultReason;
    OfferedPrice: string;
    FinalFollowup: string;
    FollowupDate: string;
    SalesAssociate: string;
    ConfidenceLevel: ConfidenceLevel;
    CommunicationMode: CommunicationMode;
    Comments: string;
    Alerted: string;
    Conversation: string;
    NextFollwup: string;
    ObjectVersion: string;
    Deleted: string;
}
export class Division{
    Name: string;
    Code: string;
}

export class Result{
    Code: string;
}

export class Lead{
    DocNumber: string;
}
export class ResultReason{
    Reason: string;
}

export class ConfidenceLevel{
    Code: string;
}

export class CommunicationMode{
    Code: string;
}

export class CreateFollowUpRequest extends BaseSearchRequest{

}

