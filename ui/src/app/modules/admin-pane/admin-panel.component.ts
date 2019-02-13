import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppRoute} from "../../app.route";
import {AdminPanelRoute} from "./admin-panel.route";

@Component({
  selector: 'app-admin-pane',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent {

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {

  }

  goToSoccerFields() {
    this.goToRoute(AdminPanelRoute.SOCCER_FIELD);
  }

  goToUser() {
    this.goToRoute(AdminPanelRoute.USER);
  }

  goToRoute(route: AdminPanelRoute) {
    this.route.routeConfig.path != route ?
      this.router.navigateByUrl(AppRoute.ADMIN_PANE + '/' + route)
      :
      this.router.navigateByUrl(AppRoute.ADMIN_PANE);
  }

}
