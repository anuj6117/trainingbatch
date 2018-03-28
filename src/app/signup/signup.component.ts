import { Component, OnInit } from '@angular/core';
import { SignUpService } from './signup.service';
import {MainService} from './../service/mainService'
import { ToasterService } from 'angular2-toaster';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  private signUpData:any = {};

  constructor(public signupService: SignUpService,private mainService:MainService, public toasterService: ToasterService) { }

  ngOnInit() {
   this.signUpData.country="";
   this.toasterService.pop('success', 'Success!', "this is success")
  }
  submitSignup()
{ 
 // console.log(this.formData)
  
  this.mainService.submitSignup(this.signUpData).subscribe(
    succes =>
    {
      console.log("succes",succes);
    })
}

}
