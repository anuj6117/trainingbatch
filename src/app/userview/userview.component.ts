import { Component, OnInit, AfterViewInit } from '@angular/core';
import {MainService} from './../service/mainService'

@Component({
  selector: 'app-userview',
  templateUrl: './userview.component.html',
  styleUrls: ['./userview.component.css']
})
export class UserviewComponent implements OnInit {
   public isShowMenu: string = "";
   public userDetail: any = [];
   
  constructor(private mainService:MainService) { }

  ngOnInit() {
    !function ($) {
      $(document).on("click","#left ul.nav li.parent > a > span.sign", function(){          
          $(this).find('i:first').toggleClass("icon-minus");      
      }); 
      // Open Le current menu
      $("#left ul.nav li.parent.active > a > span.sign").find('i:first').addClass("icon-minus");
      $("#left ul.nav li.current").parents('ul.children').addClass("in");
  
  };
  //this.isShowMenu = "userManagement";
  this.getViewUser();
  }

  getViewUser(){
    this.mainService.getViewUser().subscribe(
      response =>
      {
        if(response){
    //         this.userDetail = response.data;
        }
      //  console.log("succes",succes);
      //  this.toasterService.pop('success', 'Success!', "You have successfully created your account!")
      })
  }

  myAccFunc(type){
    // $(this).click(() => {
    //   $(this).next().removeClass("w3-hide");
    // })
    this.isShowMenu = type;
    console.log("sdfdf")
  }
}
