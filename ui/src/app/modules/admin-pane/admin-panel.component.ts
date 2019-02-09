import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppRoute} from "../../configs/app-route";

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
