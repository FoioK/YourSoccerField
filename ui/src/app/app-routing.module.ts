import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AppRoute} from "./app.route";
import {PageNotFoundComponent} from "./core/page-not-found/page-not-found.component";

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
    path: AppRoute.BOOKING + '/:id',
    loadChildren: 'src/app/modules/booking/booking.module#BookingModule'
  },
  {
    path: AppRoute.ADMIN_PANE,
    loadChildren: 'src/app/modules/admin-pane/admin-panel.module#AdminPanelModule'
  },
  {
    path: '**',
    component: PageNotFoundComponent
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
