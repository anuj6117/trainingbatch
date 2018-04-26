import { Component, OnInit } from '@angular/core';
import { MainService } from './../service/mainService';

@Component({
  selector: 'app-user-wallet',
  templateUrl: './user-wallet.component.html',
  styleUrls: ['./user-wallet.component.css']
})
export class UserWalletComponent implements OnInit {
  private walletBalance:any= [];
 // private balance:any={}
  constructor(public mainService: MainService){

  }


  ngOnInit() {
    this.getWalletBalanceCall();
  }
  getWalletBalanceCall(){
    this.mainService.getWalletBalance().subscribe(
     success=>{
       console.log("walletBalance",success.data.walletModel)
       this.walletBalance = success.data.walletModel;
       let temp = localStorage.getItem('user');
     })
  }
}
