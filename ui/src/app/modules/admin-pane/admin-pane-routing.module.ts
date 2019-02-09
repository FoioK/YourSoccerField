import {RouterModule, Routes} from "@angular/router";
import {AdminPanelComponent} from "./admin-panel.component";
import {NgModule} from "@angular/core";
import {SoccerFieldListComponent} from "./pages/soccer-field/soccer-field-list.component";
import {AdminGuard} from "../../core/guards/admin.guard";
import {AppRoute} from "../../configs/app-route";
import {UserListComponent} from "./pages/user/user-list.component";

const adminPaneRoutes: Routes = [
  {
    path: '',
    component: AdminPanelComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard],
    // children: [
    //   {
    //     path: AppRoute.ADMIN_PANE_CHILD.SOCCER_FIELD,
    //     component: SoccerFieldListComponent,
    //   }
    // ]
  },
  {
    path: AppRoute.ADMIN_PANE_CHILD.SOCCER_FIELD,
    component: SoccerFieldListComponent,
    canActivate: [AdminGuard]
  },
  {
    path: AppRoute.ADMIN_PANE_CHILD.USER,
    component: UserListComponent,
    canActivate: [AdminGuard]
  }
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
