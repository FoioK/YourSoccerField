import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AppRoute} from "../../configs/app-route";
import {RegistrationComponent} from "./pages/registration/registration.component";
import {LoginComponent} from "./pages/login/login.component";

const loginRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: LoginComponent,
  },
  {
    path: AppRoute.REGISTRATION,
    component: RegistrationComponent
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(loginRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class LoginRoutingModule {

}
