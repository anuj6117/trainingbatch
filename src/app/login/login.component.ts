import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms'; 
import { MainService } from './../service/mainService';
import { Router, ActivatedRoute } from '@angular/router';
  
  @Component  ({ 
      selector: 'app-login', 
      templateUrl: './login.component.html', 
      styleUrls: ['./login.component.css'] 
    })

export class LogInComponent implements OnInit { 
  userName: string = '';
  password: string = '';
  return: string = 'login sucessfull';
    
  private formData: any = {}; 
          constructor(private mainService:MainService,     private router: Router,
            private route: ActivatedRoute) { } 
    
      ngOnInit() { 
        this.route.queryParams
        .subscribe(params => this.return = params['return'] || '/user-dashboard');

     } 
submitLogIn() {
  this.mainService.submitLogIn(this.formData).subscribe(
    success=>
    {
        this.router.navigate(['/user-dashboard']);
    })
    
}
}