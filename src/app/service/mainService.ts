import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
@Injectable()
export class MainService  {

    constructor(private http:Http) { }
    // obj={
    //   id:1,
    //   userName:'hemant',
    //   designation:'Front-End Developre'
    // }
    // succes()
    // {
    //   return "Function Access Successfully"
    // }
    // overallDetails() {
    //     return this.http.get('http://192.168.9.180:8080/signup').map(res => res.json());
    // }

    submitSignUp(fromData){
    
      console.log('fromData',fromData);
      let data:any = {
              "userName": fromData.userName,
              "email": fromData.email,
              "password":fromData.password,
              "country":fromData.country,
              "phoneNumber" :fromData.mobileNumber
      }
         return this.http.post('http://192.168.9.180:8181/signup', data);
    }

    submitLogIn(fromData){
    
      console.log('fromData',fromData);
      let data:any = {
              
              "email": fromData.email,
              "password":fromData.password
              
      }
      
         return this.http.post('http://192.168.9.180:8181/userlogin', data);
    }


}