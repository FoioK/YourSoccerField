import {RouterModule, Routes} from "@angular/router";
import {AdminPaneComponent} from "./components/admin-pane.component";
import {NgModule} from "@angular/core";
import {AdminSoccerFieldComponent} from "./components/soccer-fields/admin-soccer-field.component";
import {AdminPaneGuard} from "../../module/admin-pane-guard";
import {AppRoute} from "../../module/app-route";

const adminPaneRoutes: Routes = [
  {
    path: '',
    component: AdminPaneComponent,
    pathMatch: 'full',
    canActivate: [AdminPaneGuard],
    children: [
      {
        path: AppRoute.ADMIN_PANE_CHILD.SOCCER_FIELD,
        component: AdminSoccerFieldComponent,
      }
    ]
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(adminPaneRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AdminPaneRoutingModule {

}
