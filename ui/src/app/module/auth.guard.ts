import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AppRoute} from './app-route';
import {AuthService} from '../service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    public router: Router,
    public userService: UserService
  ) {
  }

  canActivate(): boolean {
    if (!this.userService.getLogged()) {
      this.router.navigateByUrl(AppRoute.login);

      return false;
    }

    return true;
  }
}
