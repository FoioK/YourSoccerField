import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppRoute} from "../../../module/app-route";

@Component({
  selector: 'app-admin-pane',
  templateUrl: './admin-pane.component.html',
  styleUrls: ['./admin-pane.component.css']
})
export class AdminPaneComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {
  }

  goToSoccerFields() {
    this.router.navigate([AppRoute.ADMIN_PANE_CHILD.SOCCER_FIELD], {relativeTo: this.route});
  }

}
