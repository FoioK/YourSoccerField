import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Configuration} from './configuration';
import {ApiMapping} from './api-mapping';
import {TokenModel} from '../model/token-model';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from "../model/user";
import {catchError} from "rxjs/operators";

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
      this.configuration.apiServer + this.apiMapping.user_adminPane_authenticate,
      {headers: this.configuration.getTokenAuthorization()}
    );
  }

  createUser(user: User): Observable<HttpResponse<User>> {
    return this.http
      .post<User>(
        this.configuration.apiServer + this.apiMapping.user_create,
        user,
        {
          headers: Configuration.getJSONContentType(),
          observe: 'response'
        }
      )
      .pipe(catchError(this.errorHandler));
  }

  private errorHandler(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse.error);
  }
}
