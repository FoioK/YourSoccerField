import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AppRoute} from "../../module/app-route";

@Component({
  selector: 'navbar-page',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private router: Router) {

  }

  goToLogin() {
    this.router.navigateByUrl('/' + AppRoute.login);
  }
}
