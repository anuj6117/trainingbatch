import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MainService } from './../service/mainService';

@Component({
  selector: 'app-add-coin',
  templateUrl: './add-coin.component.html',
  styleUrls: ['./add-coin.component.css']
})
export class AddCoinComponent implements OnInit {

  public AllCurrency = [];
  public formData: any= {};

  constructor(public mainService: MainService){

  }

  ngOnInit() {
    this.getAllCurrency()
 } 

 submitAddCoin() {
  this.mainService.submitAddCoin(this.formData).subscribe(
      success=>
      {
        this.getAllCurrency()
      })
}

getAllCurrency(){
  this.mainService.getAllCurrency().subscribe(
    success=>
    {
      this.AllCurrency = success.data;
    })
}
}

