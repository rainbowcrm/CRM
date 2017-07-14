import { Injectable } from '@angular/core';

/*
  Generated class for the Loader provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class CommonHelper {
  private EXTENSIONS = {
      doc:"application/msword",
      docx: "application/msword",
      dot: "application/msword",
      htm: "text/html",
      html: "text/html",
      jpeg: "image/jpeg",
      jpg: "image/jpeg",
      png: "image/png",
      mp3: "audio/mpeg",
      pdf: "application/pdf",
      ppt: "application/vnd.ms-powerpoint",
      pps: "application/vnd.ms-powerpoint",
      txt: "text/plain",
      xlm: "application/vnd.ms-excel",
      xls: "application/vnd.ms-excel",
      zip: "application/zip",

  }
  constructor() { }

  getExtensionFromLink(file: string): string{
    return file.split('.').pop();
  }

  getMimeForExtension(file: string): string{
    let extension = this.getExtensionFromLink(file);
    return this.EXTENSIONS[extension];
  }

}
