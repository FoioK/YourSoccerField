import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppRoute} from "../../../module/app-route";
import {MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-admin-pane',
  templateUrl: './admin-pane.component.html',
  styleUrls: ['./admin-pane.component.css']
})
export class AdminPaneComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AdminPaneComponent>,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {
  }

  goToSoccerFields() {
    const soccerFieldPath = AppRoute.ADMIN_PANE_CHILD.SOCCER_FIELD;
    this.route.routeConfig.path != soccerFieldPath ?
      this.router.navigate(
        [soccerFieldPath],
        {relativeTo: this.route}
      )
      : false;
  }

}
