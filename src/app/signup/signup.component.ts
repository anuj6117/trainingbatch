import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MainService } from './../service/mainService';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})
export class SignUpComponent implements OnInit {
    private formData: any = {};
    constructor(private mainService: MainService) { }

    ngOnInit() {
        
    }
    submitSignUp() {
        
        this.mainService.submitSignUp(this.formData).subscribe(
            success=>
            {
                
            })
    }
}