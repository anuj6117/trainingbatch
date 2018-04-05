import { Component, OnInit } from '@angular/core';
import { MainService } from './../service/mainService';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {


  private UserProfile = [];
  private name() {
    
  } formData: any= {};


  constructor(public mainService: MainService){

  }

  ngOnInit() {
    this.getUserProfile()
 } 

 getUserProfile(){
  this.mainService.getUserProfile().subscribe(
    success=>
    {
      this.UserProfile = success.data;
    })
}

}

