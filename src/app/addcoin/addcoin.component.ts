
import { Component, OnInit, AfterViewInit } from '@angular/core';
import {MainService} from './../service/mainService'
import { ToasterService } from 'angular2-toaster'; // for import toaster feature
@Component({
  selector: 'app-addcoin',
  templateUrl: './addcoin.component.html',
  styleUrls: ['./addcoin.component.css']
})




export class AddcoinComponent implements OnInit {
  public isShowMenu: string = "";
  constructor(private mainService: MainService, public toasterService: ToasterService) { }
//for create side manu functionality
  ngOnInit() {
    !function ($) {
      $(document).on("click","#left ul.nav li.parent > a > span.sign", function(){          
          $(this).find('i:first').toggleClass("icon-minus");      
      }); 
      // Open Le current menu
      $("#left ul.nav li.parent.active > a > span.sign").find('i:first').addClass("icon-minus");
      $("#left ul.nav li.current").parents('ul.children').addClass("in");
  
  }
  //this.isShowMenu = "userManagement";
  this.submitNewCurrency()
  

  
}

// for submitting currency
submitNewCurrency(){
 // this.mainService.submitNewCurrency(this.AddCoinData).subscribe(
  //  succes =>
  //   {
  //     console.log("succes",succes);
  //     this.toasterService.pop('success', 'Success!', "You have successfully created new currency")
  // //  })
}



}

   

 
