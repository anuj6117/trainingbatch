import { Component, OnInit } from '@angular/core';
import { MainService } from './../service/mainService';

@Component({
  selector: 'app-get-all-user',
  templateUrl: './get-all-user.component.html',
  styleUrls: ['./get-all-user.component.css']
})
export class GetAllUserComponent implements OnInit {
  private getAllUser:any={}
  constructor(public mainService: MainService){

  }

  ngOnInit() {
    this.getAllUsercall()
 } 

 getAllUsercall(){
   this.mainService.getAllUser().subscribe(
    success=>{
      this.getAllUser = success.data;
    })
 }
}

