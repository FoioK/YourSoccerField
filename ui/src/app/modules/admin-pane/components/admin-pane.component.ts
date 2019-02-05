import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppRoute} from "../../../module/app-route";

@Component({
  selector: 'app-admin-pane',
  templateUrl: './admin-pane.component.html',
  styleUrls: ['./admin-pane.component.css']
})
export class AdminPaneComponent {

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {

  }

  goToSoccerFields() {
    this.goToRoute(AppRoute.ADMIN_PANE_CHILD.SOCCER_FIELD);
  }

  goToUser() {
    this.goToRoute(AppRoute.ADMIN_PANE_CHILD.USER);
  }

  goToRoute(route: string) {
    this.route.routeConfig.path != route ?
      this.router.navigateByUrl(AppRoute.ADMIN_PANE + '/' + route)
      :
      this.router.navigateByUrl(AppRoute.ADMIN_PANE);
  }

}
