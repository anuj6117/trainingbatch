import { Component, OnInit } from '@angular/core';
import { MainService } from '../service/mainService';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {

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