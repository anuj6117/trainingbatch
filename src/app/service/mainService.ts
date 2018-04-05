import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
@Injectable()
export class MainService  {
    
    constructor(private http:Http) { }


    submitSignUp(fromData){
    
      let data:any = {
              "userName": fromData.userName,
              "email": fromData.email,
              "password":fromData.password,
              "country":fromData.country,
              "phoneNumber" :fromData.mobileNumber
      }
         return this.http.post('http://180.151.84.102/trainingbatch/signup', data);
    }

    submitLogIn(fromData){
    
      let data:any = {
              
              "email": fromData.email,
              "password":fromData.password
              
      }
      
         return this.http.post('http://180.151.84.102/trainingbatch/userlogin', data);
    }

    submitAddCoin(fromData){
        let data:any =
        {
            "coinName":fromData.coinName,
            "symble":fromData.symbol,
            "initialSupply":fromData.supply,
            "price":fromData.price
            }
            return this.http.post('http://180.151.84.102/trainingbatch/addcurrency', data);
        }

        getAllCurrency(): Observable<any>{
            return this.http.get('http://180.151.84.102/trainingbatch/getallcurrency')
            .map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
        }


        getAllUser(): Observable<any>{
            return this.http.get('http://180.151.84.102/trainingbatch/getallusers')
            .map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
        }


        getUserProfile(): Observable<any>{
            return this.http.get('http://180.151.84.102/trainingbatch/getallusers')
            .map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
        }



}