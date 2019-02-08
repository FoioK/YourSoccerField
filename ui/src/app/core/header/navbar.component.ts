import {Component, OnInit, Renderer2} from "@angular/core";
import {Router} from "@angular/router";
import {AppRoute} from "../../configs/app-route";
import {UserService} from "../http/user/user.service";

@Component({
  selector: "navbar-page",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"]
})
export class NavbarComponent implements OnInit {
  isLogged: Boolean = false;
  private isAdmin: boolean = false;
  private arrow: Boolean = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private render: Renderer2,
  ) {
  }

  ngOnInit() {
    this.userService.isLogged()
      .subscribe(response => {
        this.isLogged = response;
        if (response) {
          this.checkIsAdmin();
          return;
        }
        this.isAdmin = false;
      });

    this.topArrow();
  }

  isUserAdmin(): boolean {
    return this.isAdmin;
  }

  goToAdminPane() {
    this.router.navigateByUrl("/" + AppRoute.ADMIN_PANE);
  }

  goToMain() {
    this.router.navigateByUrl("/" + AppRoute.MAIN_PAGE);
  }

  goToLogin() {
    this.router.navigateByUrl("/" + AppRoute.LOGIN);
  }

  logOut() {
    this.logOutProcess(this.router.url === AppRoute.MAIN_PAGE);
  }

  logOutProcess(isMainPage: Boolean) {
    this.userService.logOut();
    this.userService.isLogged().subscribe(result => this.isLogged = result);

    if (isMainPage) {
      window.location.reload();
    } else {
      this.router.navigateByUrl(AppRoute.MAIN_PAGE);
    }
  }

  private checkIsAdmin() {
    this.userService.adminPaneAuthenticate()
      .subscribe(response => this.isAdmin = response.valueOf());
  }

  private topArrow() {
    this.render.listen(window, "scroll", () => {
      this.arrow = window.scrollY > 50;
    });
  }

  private scrollTop() {
    window.scrollTo({top: 0, left: 0, behavior: "smooth"});
  }

}
