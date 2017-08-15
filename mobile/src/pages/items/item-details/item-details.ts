import { Component, ViewChild } from '@angular/core';
import { NavParams, Slides, ToastController, NavController } from 'ionic-angular';
import { ItemWithDetails, Inventory, Rating, ItemReview} from '../';
import { Storage } from '@ionic/storage';
import { CommonHelper } from '../../../providers';
import { FileOpener } from '@ionic-native/file-opener';
import { FileTransfer, FileTransferObject } from '@ionic-native/file-transfer';
import { File } from '@ionic-native/file';


/*
  Generated class for the ItemDetails page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-item-details',
  templateUrl: 'item-details.html'
})
export class ItemDetails {
  private item:ItemWithDetails;
  private inventory: Inventory;
  private slideImg: Array<String>=[];
  private isAssociateItems:Boolean;
  private itemRating: Rating;
   @ViewChild(Slides) slides: Slides;
  

  constructor(private params: NavParams, private storage: Storage, private fileOpener: FileOpener,
  private toastCtrl: ToastController, private navCtrl: NavController, private helper: CommonHelper,
  private transfer: FileTransfer, private file: File) {
    this.item = this.params.get('item');
    if(this.item.Inventory)
      this.inventory = this.item.Inventory[0];
    this.isAssociateItems = this.params.get('isAssociateItems');
    this.processImageUrl();
    this.processRating();
  }

  addToCart(){
      this.storage.get('associateItem').then((val) => {
        this.storage.remove('associateItem');
        let selectedInventory={
          item: this.item,
          inventory: this.inventory
        }
        this.storage.set("associateItem",selectedInventory);
        this.navCtrl.popToRoot();
      })
  }
  
  inventoryChanged(index){
    this.inventory = this.item.Inventory[index];
  }

  processImageUrl(){
    var imageUrls = ['Image1URL','Image2URL','Image3URL'];
    for(var l=0;l<imageUrls.length;l++){
      var image = this.item[imageUrls[l]];
      if(image){
         this.slideImg.push(image);
      }
    }
  }

  processRating(){
    var feedbacks = this.item.FeedBackLines;
    if(feedbacks && feedbacks.length > 0){
        this.itemRating = new Rating();
        this.itemRating.count = feedbacks.length;
        let totalRating = 0.0;
        for(let i=0; i<feedbacks.length; i++){
           totalRating += parseFloat(feedbacks[i].Rating);
        }
        this.itemRating.rating = (totalRating/this.itemRating.count)/2+"";
    }
  }

  ionViewDidLoad() {
    this.slides.pager = true;
  }

  download(url, name){
    url = encodeURI(url);
    const fileTransfer: FileTransferObject = this.transfer.create();
    let filePath = this.file.dataDirectory + name +"."+ this.helper.getExtensionFromLink(url)
    fileTransfer.download(url, filePath).then((entry) => {
    console.log('download complete: ' + entry.toURL());
     this.openFile(filePath)
     }, (error) => {
      // handle error
    });
     
  }

  openFile(file){
     let mime = this.helper.getMimeForExtension(file);
    if(mime == undefined || mime.length == 0){
       this.showToast("Cannot open file");
    }
    this.fileOpener.open(file, mime)
      .then(() => console.log('File is opened'))
      .catch(e => this.showToast("Cannot open file "));
  }

  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  getAllViews(): void{
     this.navCtrl.push(ItemReview, {feedbacks: this.item.FeedBackLines})
  }


 

}
