import { BaseSearchRequest } from '../../../providers/';

export class AppointmentSearchRequest extends BaseSearchRequest{
}


export class Appointment{
   Agent: Agent;
   UserId: string;
   AgentRemarks: string;
   ApptDate: string;
   ApptTime: string;
   CancelReasonAgent: string;
   CancelReasonManager: string;
   Company: Company;
   CompetitorSalesLines: Array<any>;
   CreatedBy: string;
   CreatedDate: string;
   DateString: string;
   Deleted: string;
   Description: string;
   DocNo: string;
   Doctor: Doctor;
   Duration: string;
   FeedBack: string;
   Hh: string;
   Id: string;
   LastUpdateDate: string;
   LastUpdatedBy: string;
   Location: Location;
   Manager: Manager;
   ManagerRemarks: string;
   Mm: string;
   NextAppointmentHH: string;
   NextAppointmentDate: string;
   NextAppointmentMM: string;
   OrderLines: Array<any>;
   PartyName: string;
   PartyNameWithType: string;
   PartyType: AppointmentType;
   PastAppointment: string;
   PrescriptionSurveys: string;
   PreviousFeedBack: string;
   PromotedItems: Array<PromotedItem>;
   ScheduleNextAppointment: string;
   Status: AppointmentType;
   Stockist: string;
   StockistVisitOrderLines: Array<any>;
   Store: string;
   Template: string;
   Version: string;
   VisitCompletion: string;
}

export class PromotedItem{
	Company: Company;
	CreatedBy: string;
	CreatedDate: string;
	Deleted: string;
	Id: string;
	Item: Item;
	LastUpdateDate: string;
	LastUpdatedBy: string;
	PromoDetails: string;
	RecommendedBy: string;
	Version: string;
}

export class Agent{
	UserId: string
}

export class Company{
	Name: string;
}
export class Item{
	Name: string;
}
export class Doctor{
	Name: string;
}
export class Manager{
	UserId: string;
}
export class AppointmentType{
	Code: string;
}

export class Location extends Company{

}




