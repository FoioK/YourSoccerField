import { Injectable } from "@angular/core";
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router
} from "@angular/router";
import { UserService } from "../service/user.service";
import { AppRoute } from "./app-route";
@Injectable({
  providedIn: "root"
})
export class AuthGuard implements CanActivate {
  isLogged: Boolean;

  constructor(private userService: UserService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (localStorage.getItem('token')) {
      return true;
    } else {
      this.router.navigateByUrl(AppRoute.login);
      return false;
    }
  }
}
