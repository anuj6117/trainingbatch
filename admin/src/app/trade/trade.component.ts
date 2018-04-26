import { Component, OnInit } from '@angular/core';
import { MainService } from '../service/mainService';

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit {

  getAllTransection:any={}

  constructor(public mainService: MainService){

  }

  ngOnInit() {
    this.getAllTransectionCall()
 } 
 getAllTransectionCall(){
  this.mainService.getAllTransection().subscribe(
   success=>{
     this.getAllTransection = success.data;
   })
}

}