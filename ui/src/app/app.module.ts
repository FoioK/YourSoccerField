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
import {MainPageComponent} from './component/main-page/main-page.component';
import {UserService} from './service/user.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SoccerFieldService} from './service/soccer-field.service';
import {MultirangeSliderComponent} from './component/multirange-slider/multirange-slider.component';
import {MiniSoccerfieldComponent} from './component/mini-socerfield/mini-socerfield.component';
import {FooterComponent} from './component/footer/footer.component';
import {DetailsSoccerfieldComponent} from './component/details-soccerfield/details-soccerfield.component';
import {AdminPaneComponent} from './component/admin-pane/admin-pane.component';

import {AuthGuard} from './module/auth.guard';
import {AdminPaneGuard} from "./module/admin-pane-guard";
import {ReservationService} from './service/reservation.service';
import {CalendarDateFormatter, CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';
import {CalendarComponent} from './component/calendar/calendar.component';
import {CalendarHeaderComponent} from './component/calendar/calendar-header/calendar-header.component';
import {CustomDateFormatter} from './component/calendar/custom-classes/twenty-four-hours';
import {ConvertTime12To24FormatPipe} from './component/calendar/custom-pipes/convert-time12-to24-format.pipe';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    NavbarComponent,
    MainPageComponent,
    MultirangeSliderComponent,
    MiniSoccerfieldComponent,
    FooterComponent,
    DetailsSoccerfieldComponent,
    CalendarComponent,
    CalendarHeaderComponent,
    AdminPaneComponent,
    ConvertTime12To24FormatPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }, {
      dateFormatter: {
        provide: CalendarDateFormatter,
        useClass: CustomDateFormatter
      }
    }),
  ],
  providers: [
    AuthService,
    Configuration,
    RegisterService,
    ApiMapping,
    UserService,
    SoccerFieldService,
    AuthGuard,
    ReservationService,
    AdminPaneGuard,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
