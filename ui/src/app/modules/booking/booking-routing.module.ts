import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {DetailsSoccerFieldComponent} from "./details-soccer-field.component";
import {AuthenticationGuard} from "../../core/guards/authentication.guard";

const bookingRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: DetailsSoccerFieldComponent,
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
