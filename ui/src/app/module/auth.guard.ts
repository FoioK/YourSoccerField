import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AppRoute } from './app-route';
import { AuthService } from '../service/auth.service';
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  isLogged: Boolean;

  constructor(public router: Router, public authService: AuthService) {}

  canActivate(): boolean {
    if (!this.authService.isAuthenticated()) {
      this.router.navigateByUrl(AppRoute.login);
      return false;
    }
    return true;
  }
}
