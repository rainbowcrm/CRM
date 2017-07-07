import { BaseSearchRequest } from '../../../providers/';

export class ExpenseVoucher{
  Open: string;
  Division: Division;
  DocNumber: string;
  ManagerComments: string;
  SalesAssoicate: SalesAssoicate;
  ExpenseVoucherLines: Array<ExpenseVoucherLine>;
  CorrectedTotal: string;
  RequestedTotal: number;
  AssociateComments: string;
  ExpenseDate: string;
  ApprovedTotal: string;
}

export class Division{
    Name: string;
    Code: string;
}

export class SalesAssoicate{
   UserId: string;
}

export class ExpenseVoucherLine{
  LineNumber: string;
  File2: string;
  File2Data: string;
  ManagerComments: string;
  CorrectedAmount: string;
  AssociateComments: string;
  BillNumber: string;
  FilePath: string;
  ExpenseHead: ExpenseHead;
  File1: string;
  File1Data: string;
  FileWithLink: string;
  RequestedAmount: number;
  ExpenseLocation: string;
  ApprovedAmount: string;
  ObjectVersion: string;
  Deleted: string;
}

export class ExpenseHead{
  Id: string;
  Name: string;
}

export class AddExpenseVoucherRequest extends BaseSearchRequest{

}

export class FetchExpenseHead extends BaseSearchRequest{

}
