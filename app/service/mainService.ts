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

}