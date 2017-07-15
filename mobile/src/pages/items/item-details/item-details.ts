import { Component, ViewChild } from '@angular/core';
import { NavParams, Slides, ToastController, NavController } from 'ionic-angular';
import { ItemWithDetails, Inventory} from '../';
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
   @ViewChild(Slides) slides: Slides;
  

  constructor(private params: NavParams, private storage: Storage, private fileOpener: FileOpener,
  private toastCtrl: ToastController, private navCtrl: NavController, private helper: CommonHelper,
  private transfer: FileTransfer, private file: File) {
    this.item = this.params.get('item');
    if(this.item.Inventory)
      this.inventory = this.item.Inventory[0];
    this.isAssociateItems = this.params.get('isAssociateItems');
    this.processImageUrl();
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


 

}
