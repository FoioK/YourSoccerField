import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Configuration } from './configuration';
import { ApiMapping } from './api-mapping';
import { TokenModel } from '../model/token-model';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { AppRoute } from '../module/app-route';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private apiMapping: ApiMapping,
    private router: Router
  ) {}

  private isLoggedSubject = new BehaviorSubject<boolean>(false);

  isLogged(): Observable<boolean> {
    this.setLogged(this.isAuthenticated());

    return this.isLoggedSubject.asObservable();
  }

  isAuthenticated(): boolean {
    const token: TokenModel = JSON.parse(localStorage.getItem('token'));

    return token != null && !this.jwtHelper.isTokenExpired(token.access_token);
  }

  setLogged(isLogged: boolean) {
    this.isLoggedSubject.next(isLogged);
  }

  logOut() {
    localStorage.removeItem('token');
    this.setLogged(false);
  }

  adminPaneAuthenticate(): Observable<boolean> {
    return this.http.get<boolean>(
      this.configuration.apiServer +
        this.apiMapping.user_adminPane_authenticate,
      { headers: Configuration.getTokenAuthorization() }
    );
  }

  getLoggedUserCode(): number {
    const token: TokenModel = JSON.parse(localStorage.getItem('token'));
    if (token !== null) {
      return token.code;
    } else {
      this.logOut();
      this.router.navigateByUrl(AppRoute.LOGIN);
    }
  }
}
