import { BaseSearchRequest } from '../../../providers/';

export class ReloadTopicRequest extends BaseSearchRequest{
  submitAction: String;
}

export class ReloadTopicResponse{
  result: string;
  dataObject:Discussion;
}

export class Discussion{
  CompleteDiscussion: string;
  CurrentTopic: string;
  Deleted: string;
  Id: string;
  NewReplies: string;
  NewTopic: string;
  ObjectVersion: string;
  PostedReply: string;
  ReadReply: string;
  OpenTopics: Array<Topic>;

}

export class Topic{
  Closed: string;
  Comments: string;
  Deleted: string;
  Division: Division;
  Id: string;
  ObjectVersion: string;
  Owner: Owner;
  PortfolioKey: string;
  PortfolioType: PortFolioType;
  PortfolioValue: string;
  Question: String;
  RefNo: string;
  Sales: string;
  SalesBill: string;
  SalesMade: string;
  Title: string;
  TopicDate: string;
  TopicLines:Array<TopicLine>
}

export class TopicLine{
  Comments: string;
  Deleted: string;
  Id: string;
  LineNumber: string;
  ObjectVersion: string;
  RepliedBy: Owner;
  Reply: string;
  ReplyDate: string;
  ReplyUseful: string;
  Status: string;
}


export class Division{
    Name: string;
    Code: string;
}

export class Owner{
    UserId: string;
}

export class PortFolioType{
  Code: string;
}

export class ReplyRequest{
  selectedTopic: string;
  reply: string;
  repliesRead: string;
}

export class ReadRepliesRequest extends BaseSearchRequest{
  submitAction: String;
}

export class CloseTopicRequest extends BaseSearchRequest{
  submitAction: String;
}

export class CreateDiscussionRequest extends BaseSearchRequest{
  submitAction: string;
  dataObject: NewDiscussion;
}

export class NewDiscussion{
  OpenTopics: string;
  ReadReply: string;
  PostedReply: string;
  NewTopic: Topic;
  Sales: string;
  PortfolioValue: string;
  RefNo: string;
  SalesBill: string;  
  Closed: string;
  SalesMade: string;
  Comments: string;
}

export class PortfolioTypeRequest{
  lookupType: string;
  additionalParam: string;
  searchString: string;
  from: string;
  noRecords: string;
}

