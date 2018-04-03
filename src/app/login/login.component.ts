import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MainService } from './../service/mainService'
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  private SignInData: any = {};
  constructor(private mainService:MainService, private router:Router) { }

  ngOnInit() {
   }
//for submitting signin web api service
  submitSignin() {
    this.mainService.submitSignin(this.SignInData).subscribe(
      succes=>
      {
        this.router.navigate(['/dashboard']);
        console.log('succes',succes)
      })
  }
}
