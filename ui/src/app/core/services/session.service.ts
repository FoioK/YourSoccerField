import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {TokenModel} from "../../shared/models/token-model";
import {JwtHelperService} from "@auth0/angular-jwt";
import {AppRoute} from "../../app.route";
import {Router} from "@angular/router";

@Injectable()
export class SessionService {

  private jwtHelper: JwtHelperService = new JwtHelperService();
  private isLoggedSubject = new BehaviorSubject<boolean>(false);

  constructor(private router: Router) {

  }

  isLogged(): Observable<boolean> {
    this.setLogged(this.isAuthenticated());

    return this.isLoggedSubject.asObservable();
  }

  isAuthenticated(): boolean {
    const token: TokenModel = this.getToken();

    return token != null && !this.jwtHelper.isTokenExpired(token.access_token);
  }

  getToken(): TokenModel {
    const token: TokenModel = JSON.parse(localStorage.getItem('token'));

    if (token == null) {
      this.router.navigateByUrl(AppRoute.LOGIN);
    }

    return token;
  }

  setLogged(isLogged: boolean) {
    this.isLoggedSubject.next(isLogged);
  }

  logOut() {
    localStorage.removeItem('token');
    this.setLogged(false);
  }

  getLoggedUserCode(): number {
    const token: TokenModel = this.getToken();
    if (token !== null) {
      return token.code;
    } else {
      this.logOut();
      this.router.navigateByUrl(AppRoute.LOGIN);
    }
  }

}
