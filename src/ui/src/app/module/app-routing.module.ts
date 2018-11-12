import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "../component/login/login.component";
import {RegistrationComponent} from "../component/registration/registration.component";
import {AppRoute} from "./app-route";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: AppRoute.login,
  },
  {
    path: AppRoute.login,
    component: LoginComponent,
  },
  {
    path: AppRoute.registration,
    component: RegistrationComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {

}
