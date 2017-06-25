import { Component } from '@angular/core';
import { NavParams, NavController, ToastController, AlertController, PopoverController } from 'ionic-angular';
import { Contact, ContactSearchRequest, ContactSearchResponse } from '../';
import { HTTPService, SharedService } from '../../../providers/';
import { ContactService } from '../../../plugins/';
import { SortPopOverPage }   from '../../../common/sort-helper/sort-popover';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-contact-list',
  templateUrl: 'contact-list.html'
})
export class ContactListPage {
  private contacts:Array<Contact>;
  private request: ContactSearchRequest;
  private response: ContactSearchResponse;
  private filter: Array<Object>;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;
  private sortCondition: any;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController,
              private toastCtrl: ToastController, private alertCtrl:AlertController,
              private contactService: ContactService, private sharedData: SharedService,
              private popoverCtrl: PopoverController
             ) {
    this.contacts = this.params.get('contacts');
    this.filter = this.params.get('filter');
    this.pageNumber = 0;
    this.fetchedResults = this.params.get('fetchedResults');
    this.numberOfResults = this.params.get('numberOfResults');
  }

  ionViewDidLoad() {
     
  }

  onSort(){
    var sortType = [{key: "firstName", value: "First Name"},
                    {key: "lastName", value: "Last Name"},
                    {key: "email", value: "Email"},
                    {key: "phone", value: "Phone"},
                    {key:"contactType", value:"Type"}]
    let popover = this.popoverCtrl.create(SortPopOverPage, {sortConditions: sortType});
    popover.present({});
    popover.onDidDismiss(this.dismissSortPopover.bind(this))
  }

  dismissSortPopover(data){
    if(data){
      this.sortCondition = {};
      this.sortCondition.sortCondt = data.selectedValue;
      this.sortCondition.isAscending = data.isAscending;
      this.pageNumber = -1;
      this.fetchedResults = 0;
      this.contacts = [];
      this.doSearchMoreContact({complete:function(){}});
    }
  }

  getContactAddress(contact){
     let contactAddr = [];
     var keys = ["Address1","Address2","City","ZipCode","Landmark"];
     keys.forEach(function(value, key){
       if(contact[value] && contact[value].length > 0){
           contactAddr.push(contact[value])
         }
     })
     if(contactAddr.length == 0){
     return "no information available";
     }
    else{
      return contactAddr.join(" ,");
    }

  }
  callTel(tel): void{
            window.location.href = 'tel:'+ tel;
  }

  doSearchMoreContact(infiniteScroll) {
      if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new ContactSearchRequest();
      this.request.currentmode = 'READ';
      if(this.pageNumber < 0){
        this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      }else{
        this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      }
      if(this.sortCondition){
        this.request.rds_sortdirection = this.sortCondition.isAscending ? "ASCENDING":"DESCENDING";
        this.request.rds_sortfield = this.sortCondition.sortCondt.key;
      }
      this.request.hdnPage = ++this.pageNumber;
      this.request.pageID = "contacts";
      this.request.filter = this.filter;
      this.http.processServerRequest("post",this.request, true, this.pageNumber != 0).subscribe(
                     res => this.contactSearchSuccess(res, infiniteScroll),
                     error =>  this.contactSearchError(error, infiniteScroll)); 
  }

  contactSearchSuccess(response, infiniteScroll):void{
    this.response = response;
    if(this.response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(this.response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.contacts = this.contacts.concat(this.response.dataObject);
    this.fetchedResults += this.response.fetchedRecords;
    infiniteScroll.complete();
  }
 

  contactSearchError(error,infiniteScroll){
    infiniteScroll.complete();
  }

  saveContact(contact: Contact){
     this.contactService.saveContact(contact);
  }

}
