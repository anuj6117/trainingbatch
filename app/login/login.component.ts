import { Component, OnInit } from '@angular/core';
import{MainService} from '../service/mainService'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private formData: any = {};
  constructor(private mainService:MainService) { }

  ngOnInit() {
    console.log(this.mainService.obj);
    console.log(this.mainService.succes());
    this.mainService.overallDetails().subscribe(
      succes=>
      {
        console.log('succes',succes)
      })
  }

  submitLogin() {
    console.log(this.formData)
  }
}

this.mainService.overallDetails().subscribe( succes=> { console.log('succes',succes) })