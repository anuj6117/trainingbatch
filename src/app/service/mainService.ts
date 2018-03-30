import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
@Injectable()
export class MainService  {

    constructor(private http:Http) { }
    obj={
      id:1,
      userName:'Shambhavi',
      designation:'Front-End Developre'
    }
    succes()
    {
      return "Function Access Successfully"
    }
    overallDetails() {
		return this.http.get('https://jsonplaceholder.typicode.com/posts').map(res => res.json());
  }
  submitSignup(signUpData){
    
      // console.log('fromData',fromData);
      // let data:any = {
      //         "userName": fromData.fullName,
      //         "email": fromData.email,
      //         "password":fromData.password,
      //         "country":fromData.country,
      //         "mobileNo" :fromData.number
      // }
         return this.http.post('http://192.168.9.180:8181/signup', signUpData);
    
        }
        submitSignin(SignInData){
    
          // console.log('fromData',fromData);
          // let data:any = {
          //         "userName": fromData.fullName,
          //         "email": fromData.email,
                  
          // }
             return this.http.post('http://192.168.9.180:8080/userlogin', SignInData);
        }

        getViewUser(){
          return this.http.get('http://192.168.9.200:8080/getallusers');
        }
  }