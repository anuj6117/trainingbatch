import { Component, OnInit } from '@angular/core';
import { MainService } from './../service/mainService';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

 private formData: any = {}; 
      constructor(private mainService:MainService) { } 

  ngOnInit() {  
     
 } 
 FooterComponent() {
} 
}

