import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './component/login/login.component';
import {AuthService} from "./service/auth.service";
import {Configuration} from "./service/configuration";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./module/app-routing.module";
import {RegistrationComponent} from './component/registration/registration.component';
import {RegisterService} from "./service/register.service";
import {ApiMapping} from "./service/api-mapping";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [
    AuthService,
    Configuration,
    RegisterService,
    ApiMapping,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
