import {NgModule} from "@angular/core";
import {AuthenticationService} from "./authentication/authentication.service";
import {UserService} from "./http/user/user.service";
import {SoccerFieldService} from "./http/soccer-field/soccer-field.service";
import {BookingService} from "./http/booking/booking.service";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {AdminGuard} from "./guards/admin.guard";
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {SharedModule} from "../shared/shared.module";
import {HeaderService} from "./services/header.service";
import {AppRoutingModule} from "../app-routing.module";
import {SessionService} from "./services/session.service";
import { TokenRefreshInterceptor } from './interceptors/token-refresh.interceptor';

@NgModule({
  declarations: [],
  imports: [
    SharedModule,
    AppRoutingModule,

    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [
    AuthenticationService,
    UserService,
    SoccerFieldService,
    BookingService,
    AuthenticationGuard,
    AdminGuard,
    HeaderService,
    SessionService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenRefreshInterceptor,
      multi: true,
    }
  ],
})
export class CoreModule {

}
