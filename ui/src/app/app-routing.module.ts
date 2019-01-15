import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AppRoute} from "./module/app-route";
import {LoginComponent} from "./component/login/login.component";
import {RegistrationComponent} from "./component/registration/registration.component";
import {MainPageComponent} from "./component/main-page/main-page.component";
import {DetailsSoccerfieldComponent} from "./component/details-soccerfield/details-soccerfield.component";
import {AuthGuard} from "./module/auth.guard";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: AppRoute.MAIN_PAGE
  },
  {
    path: AppRoute.LOGIN,
    component: LoginComponent
  },
  {
    path: AppRoute.REGISTRATION,
    component: RegistrationComponent
  },
  {
    path: AppRoute.MAIN_PAGE,
    component: MainPageComponent
  },
  {
    path: AppRoute.RESERVATION + AppRoute.RESERVATION_ID,
    component: DetailsSoccerfieldComponent,
    canActivate: [AuthGuard]
  },
  {
    path: AppRoute.ADMIN_PANE,
    loadChildren: 'src/app/modules/admin-pane/admin-pane.module#AdminPaneModule'
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
