import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MainService } from './../service/mainService';
import { Router, ActivatedRoute } from '@angular/router';
import { AbstractControl } from '@angular/forms';
import { EqualValidator } from '../confirm-password/confirm-password.directive'

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  any: any;
  constructor(private mainService: MainService, public router: Router) { 
       
  }

  ngOnInit() {
  }
//   submitReset() {
        
//     this.mainService.submitReset(this.any).subscribe(
//         success=>
//         {
//             console.log(success);
//             alert("Password Reset Successful")
//             this.router.navigate(['/home']);
           
           
//         })


// }
}
