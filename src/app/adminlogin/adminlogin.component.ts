
import { Component, OnInit } from '@angular/core';
import { MainService } from './../service/mainService'
@Component({
  selector: 'app-adminlogin',
  templateUrl: './adminlogin.component.html',
  styleUrls: ['./adminlogin.component.css']
})
export class AdminloginComponent implements OnInit {

  private formData: any = {};
  constructor(private mainService:MainService) { }

  ngOnInit() {
    this.mainService.overallDetails().subscribe(
      succes=>
      {
      })
  }

  submitLogin() {
    console.log(this.formData)
  }
}
