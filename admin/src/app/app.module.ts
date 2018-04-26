import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';



import { LogInComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TradeComponent } from './trade/trade.component';
import { TransactionComponent } from './transaction/transaction.component';
import { AddCoinComponent} from './add-coin/add-coin.component';
import { UpdateCoinComponent} from './update-coin/update-coin.component';
import { GetAllUserComponent} from './get-all-user/get-all-user.component';
import { AdminNavComponent} from './admin-nav/admin-nav.component';
import { MainService} from './service/mainService';
import { FooterComponent} from './footer/footer.component';


const appRoutes: Routes = [
  { path: '',
  redirectTo: '/login',
  pathMatch: 'full'
},
   { path: 'login', component: LogInComponent },
   { path: 'dashboard', component: DashboardComponent },
   { path: 'trade', component: TradeComponent },
   { path: 'transaction', component: TransactionComponent },
   { path: 'add-coin', component: AddCoinComponent },
   { path: 'get-all-user', component: GetAllUserComponent},
   { path: 'update-coin', component: UpdateCoinComponent},
   { path: 'admin-nav', component: AdminNavComponent},
   { path: 'footer', component: FooterComponent},


   { path: '**', component: LogInComponent }
];


@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    AddCoinComponent,
    GetAllUserComponent,
    AdminNavComponent,
    DashboardComponent,
    UpdateCoinComponent,
    FooterComponent,
    TradeComponent,
    TransactionComponent
  ],
imports: [
      BrowserModule,
      HttpModule,
      FormsModule,
      RouterModule.forRoot([

        {path:'add-coin', component: AddCoinComponent},
        {path:'dashboard', component: DashboardComponent},
        {path:'get-all-user', component: GetAllUserComponent},
        {path:'login', component: LogInComponent},
        {path:'trade', component: TradeComponent},
        {path:'transaction', component: TransactionComponent},
        {path:'update-coin', component: UpdateCoinComponent},

      ]
        
       
      ),
      
    ],
  
  providers: [MainService],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
