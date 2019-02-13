import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AppRoute} from '../../app.route';
import {SessionService} from "../services/session.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(
    public router: Router,
    public sessionService: SessionService
  ) {
  }

  canActivate(): boolean {
    if (!this.sessionService.isAuthenticated()) {
      this.router.navigateByUrl(AppRoute.LOGIN);

      return false;
    }

    return true;
  }
}
