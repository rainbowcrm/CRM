export class BaseSearchRequest{
    fixedAction:string;
    pageID:string;
    currentmode:string;
    filter:Array<any>;
    hdnPage: number;
    dataObject: Object;
    rds_sortfield:string;
    rds_sortdirection:string;
    rds_selectedids:string;
}
