import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
@Injectable()
export class MainService {

  
    constructor(private http: Http) { }

    // submit signup data
    submitSignUpApi(fromData) {
        console.log("fromData ", fromData);
        let data: any = {
            "userName": fromData.userName,
            "email": fromData.email,
            "password": fromData.password,
            "country": fromData.country,
            "phoneNumber": fromData.phoneNumber,

        }
        return this.http.post('http://180.151.84.102/trainingbatch/signup', data).map(res => res.json());
    }
    // submit login data
    submitLogInApi(fromData) {

        let data: any = {

            "email": fromData.email,
            "password": fromData.password

        }

        return this.http.post('http://180.151.84.102/trainingbatch/userlogin', data);
    }

    //now next function for admin api

    // submit add new coin
    submitAddCoin(fromData) {
        let data: any =
            {
                "coinName": fromData.coinName,
                "symbol": fromData.symbol,
                "initialSupply": fromData.supply,
                "price": fromData.price
            }
        return this.http.post('http://180.151.84.102/trainingbatch/addcurrency', data).map(res => res.json());
    }

    // update coin data

    editCoin(coinId) {
        const uri = 'http://180.151.84.102/trainingbatch/addcurrency/edit/' + coinId;
        return this
                .http
                .get(uri)
                .map(res => {
                  return res;
                });
      }
    
    //   updateCoin(coinName, symbol, initialSupply, price) {
    //     const uri = 'http://180.151.84.102/trainingbatch/addcurrency/update/' + coinId;
    
    //     const obj = {
    //         coinName: updateCoinName,
    //         symbol: updateSymbol,
    //         initialSupply: updateSupply,
    //         price: updateCoinPrice

    //     };
    //     this
    //       .http
    //       .post(uri, obj)
    //       .subscribe(res => console.log('Done'));
    //   }



    submitUpdateCurrency(fromData) {
        let data: any =
            {
                "coinName": fromData.updateCoinName,
                "symbol": fromData.updateSymbol,
                "initialSupply": fromData.updateSupply,
                "price": fromData.updateCoinPrice
            }
            return this.http.post('http://180.151.84.102/trainingbatch/addcurrency', data).map(res => res.json());
        
    }

    // featch the all coin data in table
    getAllCurrency() {
        return this.http.get('http://180.151.84.102/trainingbatch/getallcurrency').map((res: Response) => res.json())
    }

    //featch the data all user in table

    getAllUser() {
        return this.http.get('http://180.151.84.102/trainingbatch/getallusers').map((res: Response) => res.json())
    }



    getWalletBalance() {
        return this.http.get('http://180.151.84.102/trainingbatch/getbyuserid?userid=1').map((res: Response) => res.json())
    }









    // getUserProfile(): Observable<any> {
    //     return this.http.get('http://180.151.84.102/trainingbatch/getallusers')
    //         .map((res: Response) => res.json())
    //         .catch((error: any) => Observable.throw(error.json().error || 'Server error'))
    // }

    // submitReset(fromData) {
    //     let data: any = {

    //         "oldPassword": fromData.oldPassword,
    //         "newPassword": fromData.newPassword,
    //         "confirmPassword": fromData.confirmPassword,

    //     }
    //     return this.http.post('http://180.151.84.102/trainingbatch/', data);
    // }

}