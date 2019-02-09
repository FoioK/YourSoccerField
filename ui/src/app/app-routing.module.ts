import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AppRoute} from "./configs/app-route";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: AppRoute.HOME
  },
  {
    path: AppRoute.LOGIN,
    loadChildren: 'src/app/modules/login/login.module#LoginModule'
  },
  {
    path: AppRoute.HOME,
    loadChildren: 'src/app/modules/home/home.module#HomeModule'
  },
  {
    path: AppRoute.RESERVATION + AppRoute.RESERVATION_ID,
    loadChildren: 'src/app/modules/booking/booking.module#BookingModule'
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
