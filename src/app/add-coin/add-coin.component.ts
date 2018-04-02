import { Component, OnInit } from '@angular/core';
import { MainService } from './../service/mainService';

@Component({
  selector: 'app-add-coin',
  templateUrl: './add-coin.component.html',
  styleUrls: ['./add-coin.component.css']
})
export class AddCoinComponent implements OnInit {
  public formData: any= {};

  constructor(public mainService: MainService){

  }

  ngOnInit() {
 } 

 submitAddCoin() {
  this.mainService.submitAddCoin(this.formData).subscribe(
      success=>
      {
          console.log("success",success);
      })
}
}

