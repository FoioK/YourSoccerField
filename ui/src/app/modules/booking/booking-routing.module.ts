import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {DetailsSoccerfieldComponent} from "./details-soccerfield.component";
import {AuthenticationGuard} from "../../core/guards/authentication.guard";

const bookingRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: DetailsSoccerfieldComponent,
    canActivate: [AuthenticationGuard]
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(bookingRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class BookingRoutingModule {

}
