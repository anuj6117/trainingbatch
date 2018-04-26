import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';


import { SignUpComponent } from './signup/signup.component';
import { LogInComponent } from './login/login.component';

import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { SettingComponent } from './setting/setting.component';
import { UserWalletComponent } from './user-wallet/user-wallet.component';
import { MainService} from './service/mainService';
import { UserNavComponent} from './user-nav/user-nav.component';
import { FooterComponent} from './footer/footer.component';
import { HomeComponent} from './home/home.component';
import { UserDashboardComponent} from './user-dashboard/user-dashboard.component';
import { EqualValidator } from './confirm-password/confirm-password.directive';
import { Bookmygain} from './bookmygain/bookmygain.component';

import { importExpr } from '@angular/compiler/src/output/output_ast';


const appRoutes: Routes = [
  { path: '',
  redirectTo: '/home',
  pathMatch: 'full'
},
   { path: 'signup', component: SignUpComponent },
   { path: 'login', component: LogInComponent },
   { path: 'resetPassword', component: ResetPasswordComponent },
   { path: 'forgetPassword', component: ForgetPasswordComponent },
   { path: 'setting', component: SettingComponent },
   { path: 'user-wallet', component: UserWalletComponent },
   { path: 'user-nav', component: UserNavComponent},
   { path: 'footer', component: FooterComponent},
   { path: 'home', component: HomeComponent },
   { path: 'user-dashboard', component: UserDashboardComponent},
   { path: 'bookmygain', component: Bookmygain}, 
 
  { path: '**', component: HomeComponent }
];


@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LogInComponent,
    ResetPasswordComponent,
    ForgetPasswordComponent,
    SettingComponent,
    UserWalletComponent,
    FooterComponent,
    HomeComponent,
    UserNavComponent,
    UserDashboardComponent,
    EqualValidator,
    Bookmygain,
  ],
  
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(
      [

        {path:'home', component: HomeComponent},
        { path: 'user-wallet', component: UserWalletComponent },
        { path: 'user-dashboard', component: UserDashboardComponent},
        {path:'login', component: LogInComponent},
        { path: 'signup', component: SignUpComponent },
        { path: 'forgetPassword', component: ForgetPasswordComponent },
        { path: 'bookmygain', component: Bookmygain},

      ]
     
    ),
    
  ],
  providers: [MainService, EqualValidator],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
