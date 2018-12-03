import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AppRoute} from "../../module/app-route";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'navbar-page',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router,
              private userService: UserService) {

  }

  isLogged: Boolean;

  ngOnInit() {
    if (localStorage.getItem('token')) {
      this.isLogged = true;
    }

    this.userService
      .isLogged()
      .subscribe(response => {
        this.isLogged = response;
      }); //TODO error handle
  }

  goToLogin() {
    this.router.navigateByUrl('/' + AppRoute.login);
  }

  logOut() {
    this.logOutProcess(this.router.url === AppRoute.mainPage);
  }

  logOutProcess(isMainPage: Boolean) {
    this.userService.logOut();

    if (isMainPage) {
      window.location.reload();
    } else {
      this.router.navigateByUrl(AppRoute.mainPage)
    }
  }
}
