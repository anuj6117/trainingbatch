import { Component, OnInit } from '@angular/core';
import { MainService } from '../service/mainService';

@Component({
  selector: 'app-dashbord',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public AllUser = [];
  public formData: any= {};

  constructor(public mainService: MainService){

  }

  ngOnInit() {
    this.getAllUser()
 } 



 getAllUser(){
  this.mainService.getAllUser().subscribe(
    success=>
    {
      this.AllUser = success.data;
    })
}
}