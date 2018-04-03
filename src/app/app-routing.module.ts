import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { SettingComponent } from './setting/setting.component';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { HomeComponent } from './home/home.component';
import { BuyComponent } from './buy/buy.component';
import { SellComponent } from './sell/sell.component';
import { SubmitComponent } from './submit/submit.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ModalModule } from 'ngx-bootstrap/modal';
import {MainService} from './service/mainService';
import { HttpModule } from '@angular/http';
import { HeaderComponent } from './header/header.component';
import { LinkComponent } from './link/link.component';
import { NavComponent } from './nav/nav.component';
import { FooterComponent } from './footer/footer.component';
import { AdminloginComponent } from './adminlogin/adminlogin.component';
import { UserviewComponent } from './userview/userview.component';
import { UserdashboardComponent } from './userdashboard/userdashboard.component';
import { AddcoinComponent } from './addcoin/addcoin.component';
import { ViewcoinComponent } from './viewcoin/viewcoin.component';
import { EditcoinComponent } from './editcoin/editcoin.component';


const appRoutes: Routes = [
  { path: 'signup', component: SignupComponent },
   { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
   { path: 'resetPassword', component: ResetPasswordComponent },
   { path: 'forgetPassword', component: ForgetPasswordComponent },
   { path: 'setting', component: SettingComponent },
   { path: 'buy', component: BuyComponent },
   { path: 'sell', component: SellComponent },
   { path: 'nav', component: NavComponent },
   { path: 'home', component: HomeComponent },
   { path: 'footer', component: FooterComponent },
   { path: 'adminlogin', component: AdminloginComponent },
   { path: 'userview', component: UserviewComponent },
   { path: 'addcoin', component: AddcoinComponent },
 { path: 'userdashboard', component: UserdashboardComponent },
 { path: 'viewcoin', component: ViewcoinComponent },
 { path: 'editcoin', component: EditcoinComponent },
   { path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  { path: '**', component: LoginComponent }
];


@NgModule({
  
  
  imports: [
    
    RouterModule.forRoot(

      appRoutes,
      // { enableTracing: true } /ng / <-- debugging purposes only
    ),
    // other imports here
    FormsModule
  ],
exports:[RouterModule]

})
export class AppRoutingModule { }
