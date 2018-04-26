import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MainService } from './../service/mainService';
import { Router, ActivatedRoute } from '@angular/router';
import { AbstractControl } from '@angular/forms';
import { EqualValidator } from '../confirm-password/confirm-password.directive'


@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})
export class SignUpComponent implements OnInit {
    private formData: any = {};
    constructor(private mainService: MainService, public router: Router) { 
       
    }

    ngOnInit() {
        
    }
    submitSignUp() {
        
        this.mainService.submitSignUpApi(this.formData).subscribe(
            success=>
            {
                console.log(success);
                alert("Sign Up Successful")
                this.router.navigate(['/login']);
               
               
            })


    }

}