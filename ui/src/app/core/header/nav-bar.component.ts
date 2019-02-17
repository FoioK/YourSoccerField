import {Component, OnInit, Renderer2} from "@angular/core";
import {Router} from "@angular/router";
import {AppRoute} from "../../app.route";
import {SessionService} from "../services/session.service";
import {UserService} from "../http/user/user.service";

@Component({
  selector: "navbar-page",
  templateUrl: "./nav-bar.component.html",
  styleUrls: ["./nav-bar.component.css"]
})
export class NavBarComponent implements OnInit {

  private isLogged: Boolean = false;
  private isAdmin: Boolean = false;
  private arrow: Boolean = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private sessionService: SessionService,
    private render: Renderer2,
  ) {

  }

  ngOnInit() {
    this.sessionService.isLogged()
      .subscribe(response => {
        this.isLogged = response;
        if (response) {
          // this.checkIsAdmin();

          return;
        }
        this.isAdmin = false;
      });

    this.topArrow();
  }

  private isUserAdmin(): Boolean {
    return this.isAdmin;
  }

  private goToAdminPane() {
    this.router.navigateByUrl("/" + AppRoute.ADMIN_PANE);
  }

  private goToHome() {
    this.router.navigateByUrl("/" + AppRoute.HOME);
  }

  private goToLogin() {
    this.router.navigateByUrl("/" + AppRoute.LOGIN);
  }

  private logOut() {
    this.logOutProcess(this.router.url === AppRoute.HOME);
  }

  private logOutProcess(isHomePage: Boolean) {
    this.sessionService.logOut();
    this.sessionService.isLogged()
      .subscribe(result => this.isLogged = result);

    if (isHomePage) {
      window.location.reload();
    } else {
      this.router.navigateByUrl(AppRoute.HOME);
    }
  }

  private checkIsAdmin() {
    this.userService.adminPaneAuthenticate()
      .subscribe(response => this.isAdmin = response.valueOf(),
      error => {
        console.log(error);
    });
  }

  private topArrow() {
    this.render.listen(
      window,
      "scroll",
      () => {
        this.arrow = window.scrollY > 50;
      }
    );
  }

  private scrollTop() {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
  }

}
