import {NgModule} from "@angular/core";
import {RegistrationComponent} from "./pages/registration/registration.component";
import {LoginRoutingModule} from "./login-routing.module";
import {LoginComponent} from "./pages/login/login.component";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
  ],
  imports: [
    SharedModule,
    LoginRoutingModule,
  ],
  providers: []
})
export class LoginModule {

}
