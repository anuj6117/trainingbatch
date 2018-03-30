import { Component, OnInit, AfterViewInit } from '@angular/core';
@Component({
  selector: 'app-viewcoin',
  templateUrl: './viewcoin.component.html',
  styleUrls: ['./viewcoin.component.css']
})
export class ViewcoinComponent implements OnInit {





   public isShowMenu: string = "";
  constructor() { }

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
  }

  myAccFunc(type){
    // $(this).click(() => {
    //   $(this).next().removeClass("w3-hide");
    // })
    this.isShowMenu = type;
    console.log("sdfdf")
  }
}
