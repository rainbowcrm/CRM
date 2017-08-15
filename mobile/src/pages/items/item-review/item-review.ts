import { Component } from '@angular/core';
import { NavController,NavParams } from 'ionic-angular';
import { FeedBackLine} from '../';



/*
  Generated class for the ItemReview page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-item-review',
  templateUrl: 'item-review.html'
})
export class ItemReview {
  private feedbacks:Array<FeedBackLine>;
  

  constructor(public navCtrl: NavController, private params: NavParams) {
    this.feedbacks = this.params.get("feedbacks");
  }

  getRating(rating: string){
    return parseFloat(rating)/2;
  }

}
