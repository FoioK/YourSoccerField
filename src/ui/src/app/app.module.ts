import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './component/login/login.component';
import {AuthService} from "./service/auth.service";
import {Configuration} from "./service/configuration";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
  ],
  providers: [AuthService, Configuration],
  bootstrap: [AppComponent]
})
export class AppModule { }
