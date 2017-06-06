import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, Content } from 'ionic-angular';
import { Topic, ReplyRequest, ReadRepliesRequest } from '../';
import { HTTPService } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-discussion-topic',
  templateUrl: 'discussion-topic.html'
})
export class DiscussionTopicList {
  private topic:Topic;
  private replyRequest: ReplyRequest;
  private readReplyRequest: ReadRepliesRequest;
  private reloadTask;
  private user;
  @ViewChild(Content) content: Content;
 /* private response: ReloadTopicResponse;*/

  constructor(private http:HTTPService,
              private navCtrl: NavController, private params: NavParams) {
        this.topic = this.params.get("topic");  
        this.user = this.params.get("user");   
  }

  ionViewDidEnter() {
    this.reloadTask = setInterval( () => {
         // this.refreshData();
      }, 1000);
  }

  ionViewDidLeave(){
    clearInterval(this.reloadTask);
  }

  refreshData(){
     this.readReplyRequest = new ReadRepliesRequest();
     this.readReplyRequest.submitAction = "fetchLatest";
     this.readReplyRequest.pageID = "topic";
     this.readReplyRequest.currentmode = "CREATE";
     this.readReplyRequest.dataObject = {
       ReadReply: "0",
       CurrentTopic: this.topic.Id
     }
     this.http.processServerRequest("post",this.readReplyRequest, true, true).subscribe(
                     res => this.discussionTopicSuccess(res),
                     error => {}); 
  }

  discussionTopicSuccess(res){
    let previousLength = this.topic.TopicLines.length;
    this.topic.TopicLines = res.dataObject.NewReplies;
    if(previousLength != res.dataObject.NewReplies.length){
      setTimeout( () => {
          this.content.scrollToBottom(500);
      }, 1000);
    }
    
  }



  reply(text: string){
    this.replyRequest = new ReplyRequest();
    this.replyRequest.repliesRead = "1";//dont know what is this????
    this.replyRequest.reply = text;
    this.replyRequest.selectedTopic = this.topic.Id;
    this.http.processCustomUrlServerRequest("ajxService=replyTopic","post",this.replyRequest, true).subscribe(
                     res =>  {
                       this.processReplyResponse(res);
                      },
                     error =>  {
                       this.processReplyError(error);
                     });
   }

   processReplyResponse(res){
     debugger
   }

   processReplyError(error){

   }
}

