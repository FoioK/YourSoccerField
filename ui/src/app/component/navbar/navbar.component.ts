import {
  Component,
  OnInit,
  Renderer2
} from "@angular/core";
import { Router } from "@angular/router";
import { AppRoute } from "../../module/app-route";
import { UserService } from "../../service/user.service";

@Component({
  selector: "navbar-page",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"]
})
export class NavbarComponent implements OnInit {
  constructor(
    private router: Router,
    private userService: UserService,
    private render: Renderer2
  ) {}

  private arrow: Boolean = false;
  isLogged: Boolean = false;

  ngOnInit() {
    if (localStorage.getItem('token')) {
      this.isLogged = true;
    } else {
      this.userService.isLogged().subscribe(response => {
        this.isLogged = response;
      }); //TODO error handle
    }
    this.topArrow();
  }

  private topArrow() {
    this.render.listen(window, "scroll", () => {
      if (window.scrollY > 50) {
        this.arrow = true;
      } else {
        this.arrow = false;
      }
    });
  }

  private scrollTop() {
    window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
  }

  goToMain() {
    this.router.navigateByUrl("/" + AppRoute.mainPage);
  }

  goToLogin() {
    this.router.navigateByUrl("/" + AppRoute.login);
  }

  logOut() {
    this.logOutProcess(this.router.url === AppRoute.mainPage);
  }

  logOutProcess(isMainPage: Boolean) {
    this.userService.logOut();
    this.userService.isLogged().subscribe(result => {
      this.isLogged = result;
    });
    if (isMainPage) {
      window.location.reload();
    } else {
      this.router.navigateByUrl(AppRoute.mainPage);
    }
  }
}
