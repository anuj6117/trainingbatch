import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MainService } from './../service/mainService';
import { headersToString } from 'selenium-webdriver/http';
import 'rxjs/add/operator/toPromise';

@Component({
  selector: 'app-add-coin',
  templateUrl: './add-coin.component.html',
  styleUrls: ['./add-coin.component.css']
})
export class AddCoinComponent implements OnInit {

  public allCurrency = [];
  public formData: any = {};

  constructor(public mainService: MainService) {

  }
  coinId:"";
  
  private headers = new Headers ({'Contant-Type': 'application/json'});
// function delete coin
  // deleteCoin = function(){
  //   if(confirm("Are You Sure Delete This Coin?")){
  //     const url = `${"http://180.151.84.102/trainingbatch/getallcurrency"}/${coinId}`;
  //     return this.http.delete(url, {headers: this.headers}).toPromise()
  //     .then(() =>{
  //       this.getAllCurrency();
  //     }
  //   )
  //   }
  // }

  ngOnInit() {
    this.getAllCurrency()
  }
  // add new coin
  submitAddCoin() {
    this.mainService.submitAddCoin(this.formData).subscribe(
      success => {
        this.getAllCurrency();

        alert("New Coin Successfull")
      })
  }
  // get all coin in table
  getAllCurrency() {
    this.mainService.getAllCurrency().subscribe(
      success => {
        this.allCurrency = success.data;
      })
  }

//   updateCoin(updateCoinName, updateSymbol,updateSupply,updateCoinPrice) {
//     this.route.params.subscribe(params => {
//     this.service.updateCoin(coinName, symbol,price, params['CoinId']);
//     this.router.navigate(['index']);
//   });
// }
// update coin

// submitUpdateCoin() {
//   this.mainService.submitUpdateCurrency(this.formData).subscribe(
//     success => {
//       this.submitUpdateCoin();

//       alert("Update Coin Successfull")
//     })
// }
editCurrency(data:any)
{
  console.log("update",data);
}
}

