import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { Discussion, ReloadTopicRequest, ReloadTopicResponse, Topic, DiscussionTopicList, DiscussionAddPage } from '../';
import { HTTPService } from '../../../providers/';
import { HomePage } from '../../home/home';
import { Storage } from '@ionic/storage';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-discussion-home',
  templateUrl: 'discussion-home.html'
})
export class DiscussionsList {
  private topics:Array<Topic>;
  private request: ReloadTopicRequest;
  private response: ReloadTopicResponse;
  private user;

  constructor(private http:HTTPService,
              private navCtrl: NavController,
              private storage: Storage) {
      this.storage.get('user').then((val) => {
         this.user = val;    
       })
  }

  ionViewDidEnter() {
     this.searchDiscussion();
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }


  searchDiscussion() {
      this.request = new ReloadTopicRequest();
      this.request.currentmode = 'CREATE';
      this.request.pageID = "topic";
      this.request.submitAction = "Refresh";
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.discussionSearchSuccess(res),
                     error =>  this.discussionSearchError(error)); 
  }

  discussionSearchSuccess(response):void{
    this.response = response;
    if(this.response.result == "failure"){
       return ;
    }
    this.topics = this.response.dataObject.OpenTopics;
  }
 

  discussionSearchError(error){
    this.http.setAuthToken(null);
  }

  getTopicSubHeader(topic: Topic){
    let subheader = "";
    if(topic.TopicLines.length > 0){
      subheader = topic.TopicLines[topic.TopicLines.length - 1].Reply;
    }
    return subheader;
  }

  navigateToTopic(topic: Topic){
    this.navCtrl.push(DiscussionTopicList, {topic: topic, user: this.user});
  }

  addDiscussion(){
    this.navCtrl.push(DiscussionAddPage);
  }

}
