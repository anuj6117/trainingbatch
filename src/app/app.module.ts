import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
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
import { AppRoutingModule } from './app-routing.module';
import { AdminloginComponent } from './adminlogin/adminlogin.component';
import { UserviewComponent } from './userview/userview.component';
import { UserdashboardComponent } from './userdashboard/userdashboard.component';
import { ToasterModule, ToasterService } from 'angular2-toaster';



import { EqualValidator } from './signup/confirmPassword.directive';
import { SignUpService } from './signup/signup.service';


import { ResourceService } from './shared/resource.component';
import { EmailComponent } from './email/email.component';
import { AddcoinComponent } from './addcoin/addcoin.component';
import { ViewcoinComponent } from './viewcoin/viewcoin.component';


@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
   DashboardComponent,
    ResetPasswordComponent,
    ForgetPasswordComponent,
    SettingComponent,
    BuyComponent,
    SellComponent,
    SubmitComponent,
    HomeComponent,
    HeaderComponent,
    LinkComponent,
    NavComponent,
    FooterComponent,
    AdminloginComponent,
    UserviewComponent,
    UserdashboardComponent,
    EqualValidator,
    EmailComponent,
    AddcoinComponent,
    ViewcoinComponent
  ],
  
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpModule,
    ToasterModule,
    BrowserAnimationsModule,
    NgbModule.forRoot(),
    CarouselModule.forRoot(),
  
    ModalModule.forRoot(),
    // other imports here
    FormsModule
  ],
  providers: [MainService, ResourceService, SignUpService,ToasterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
