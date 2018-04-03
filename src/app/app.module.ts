import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';

import { SignUpComponent } from './../../src/app/signup/signup.component';
import { LogInComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { SettingComponent } from './setting/setting.component';
import { BuyComponent } from './buy/buy.component';
import { SellComponent } from './sell/sell.component';
import {MainService} from './service/mainService';
import {AddCoinComponent} from './add-coin/add-coin.component';
import {UpdateCoinComponent} from './update-coin/update-coin.component';
import {AdminSideMenuComponent} from './admin-side-menu/admin-side-menu.component';
import {AdminNavComponent} from './admin-nav/admin-nav.component';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';
import {UserNavComponent} from './user-nav/user-nav.component';

import { importExpr } from '@angular/compiler/src/output/output_ast';


const appRoutes: Routes = [
  { path: 'signup', component: SignUpComponent },
   { path: 'login', component: LogInComponent },
   { path: 'dashboard', component: DashboardComponent },
   { path: 'resetPassword', component: ResetPasswordComponent },
   { path: 'forgetPassword', component: ForgetPasswordComponent },
   { path: 'setting', component: SettingComponent },
   { path: 'buy', component: BuyComponent },
   { path: 'sell', component: SellComponent },
   { path: 'add-coin', component: AddCoinComponent },
   { path: 'admin-side-menu', component: AdminSideMenuComponent},
   { path: 'update-coin', component: UpdateCoinComponent},
   { path: 'admin-nav', component: AdminNavComponent},
   { path: 'admin-dashboard', component: AdminDashboardComponent},
   { path: 'user-nav', component: UserNavComponent},
   
  { path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  { path: '**', component: LogInComponent }
];


@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LogInComponent,
    DashboardComponent,
    ResetPasswordComponent,
    ForgetPasswordComponent,
    SettingComponent,
    BuyComponent,
    SellComponent,
    AddCoinComponent,
    AdminSideMenuComponent,
    AdminNavComponent,
    AdminDashboardComponent,
    UpdateCoinComponent,
    UserNavComponent,
    
  ],
  
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(

      appRoutes,
     
    ),
    
  ],
  providers: [MainService],
  bootstrap: [AppComponent]
})
export class AppModule { }
