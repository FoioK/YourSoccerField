import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AppRoute} from '../../app.route';
import {UserService} from "../http/user/user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(
    public router: Router,
    public userService: UserService
  ) {
  }

  canActivate(): boolean {
    if (!this.userService.isAuthenticated()) {
      this.router.navigateByUrl(AppRoute.LOGIN);

      return false;
    }

    return true;
  }
}
