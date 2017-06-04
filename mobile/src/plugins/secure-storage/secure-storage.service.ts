import { Injectable } from '@angular/core';
import { SecureStorage, SecureStorageObject } from '@ionic-native/secure-storage';

@Injectable()
export class SecureStorageService {
  public storage: SecureStorageObject;
  constructor(private secureStorage: SecureStorage) {
      this.secureStorage.create('primussol_db')
        .then((storage: SecureStorageObject) => {
           this.storage = storage;
        });
   }

   getValue(key:string):any{
       return this.storage.get(key);
   }
   setValue(key:string, value:string):any{
       return this.storage.set(key,value);
   }
   removeValue(key:string):any{
       return this.storage.remove(key);
   }
}