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
import {NavbarComponent} from './component/navbar/navbar.component';
import { MainPageComponent } from './component/main-page/main-page.component';
import {UserService} from "./service/user.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    NavbarComponent,
    MainPageComponent
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
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
