import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms'; 
import { MainService } from './../service/mainService' 
  
  @Component  ({ 
      selector: 'app-login', 
      templateUrl: './login.component.html', 
      styleUrls: ['./login.component.css'] 
    })

export class LogInComponent implements OnInit { 
    
  private formData: any = {}; 
          constructor(private mainService:MainService) { } 
    
      ngOnInit() { 
      

     } 
submitLogIn() {
   console.log(this.formData) 
   this.mainService.submitLogIn(this.formData).subscribe(
    success=>
    {
        console.log("success",success);
    })
  } 
}