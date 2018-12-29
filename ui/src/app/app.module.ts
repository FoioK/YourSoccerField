import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './component/login/login.component';
import {AuthService} from './service/auth.service';
import {Configuration} from './service/configuration';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './module/app-routing.module';
import {RegistrationComponent} from './component/registration/registration.component';
import {RegisterService} from './service/register.service';
import {ApiMapping} from './service/api-mapping';
import {NavbarComponent} from './component/navbar/navbar.component';
import { MainPageComponent } from './component/main-page/main-page.component';
import {UserService} from './service/user.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {SoccerFieldService} from './service/soccer-field.service';
import { MultirangeSliderComponent } from './component/multirange-slider/multirange-slider.component';
import { MiniSocerfieldComponent } from './component/mini-socerfield/mini-socerfield.component';
import { FooterComponent } from './component/footer/footer.component';
import { DetailsSoccerfieldComponent } from './component/details-soccerfield/details-soccerfield.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    NavbarComponent,
    MainPageComponent,
    MultirangeSliderComponent,
    MiniSocerfieldComponent,
    FooterComponent,
    DetailsSoccerfieldComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [
    AuthService,
    Configuration,
    RegisterService,
    ApiMapping,
    UserService,
    SoccerFieldService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
