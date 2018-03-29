import { Component, OnInit } from '@angular/core';
import { MainService } from './../service/mainService';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {

 private formData: any = {}; 
      constructor(private mainService:MainService) { } 

  ngOnInit() { console.log(this.mainService.submitLogIn); 
  
    console.log(this.mainService.submitLogIn )
     
 } 
 ForgetPasswordComponent() {
console.log(this.formData) 
} 
}

