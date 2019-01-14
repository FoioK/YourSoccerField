import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Configuration} from './configuration';
import {ApiMapping} from './api-mapping';
import {TokenModel} from '../model/token-model';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private apiMapping: ApiMapping,
  ) {

  }

  private isLoggedSubject = new BehaviorSubject<boolean>(false);

  isLogged(): Observable<boolean> {
    this.setLogged(this.getLogged());

    return this.isLoggedSubject.asObservable();
  }

  getLogged(): boolean {
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
      this.configuration.apiServer + this.apiMapping.user_adminPane_authenticate,
      {headers: Configuration.getTokenAuthorization()}
    );
  }
}
