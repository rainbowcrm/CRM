import { Injectable } from '@angular/core';
import { Camera, CameraOptions } from '@ionic-native/camera';

@Injectable()
export class ImagePickerService {
  constructor(private imagePicker: Camera) { }

  getOnePicture(width, height):Promise<any>{
      const options: CameraOptions = {
        allowEdit:true,
        targetWidth:width,
        targetHeight: height,
        destinationType: 0,
        quality: 100
      };
      return this.imagePicker.getPicture(options);
  }

  getPictureFromGallery(width, height):Promise<any>{
      const options: CameraOptions = {
        sourceType:0,
        allowEdit:true,
        targetWidth:width,
        targetHeight: height,
        destinationType: 0,
        quality: 100
      };
      return this.imagePicker.getPicture(options);
  }
}