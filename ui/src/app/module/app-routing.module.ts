import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "../component/login/login.component";
import {RegistrationComponent} from "../component/registration/registration.component";
import {AppRoute} from "./app-route";
import {MainPageComponent} from "../component/main-page/main-page.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: AppRoute.mainPage,
  },
  {
    path: AppRoute.login,
    component: LoginComponent,
  },
  {
    path: AppRoute.registration,
    component: RegistrationComponent,
  },
  {
    path: AppRoute.mainPage,
    component: MainPageComponent
  }
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
