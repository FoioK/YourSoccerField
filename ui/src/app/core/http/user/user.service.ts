import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Configuration} from '../../../configs/configuration';
import {TokenModel} from '../../../shared/models/token-model';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';
import {AppRoute} from '../../../app.route';
import {User} from "../../../shared/models/user";
import {catchError} from "rxjs/operators";
import {HeaderService} from "../../services/header.service";
import {ApiRoute, PATH_USER_ID} from "../api.route";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private jwtHelper: JwtHelperService = new JwtHelperService();
  private isLoggedSubject = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private router: Router,
    private headerService: HeaderService
  ) {

  }

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
      ApiRoute.USERS_ADMIN_AUTHENTICATE,
      {headers: this.headerService.getTokenAuthorization()}
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

  createUser(user: User): Observable<HttpResponse<User>> {
    return this.http
      .post<User>(
        this.configuration.apiServer + ApiRoute.USERS,
        user,
        {
          headers: HeaderService.getJSONContentType(),
          observe: 'response'
        }
      )
      .pipe(catchError(this.errorHandler));
  }

  findAll(): Observable<Array<User>> {
    return this.http.get<Array<User>>(
      this.configuration.apiServer +
      ApiRoute.USERS,
      {
        headers: HeaderService.getJSONContentTypeWithToken()
      }
    )
  }

  updateUser(user: User) {
    return this.http.put(
      this.configuration.apiServer +
      ApiRoute.USERS_WITH_ID.replace(PATH_USER_ID, (user.id || "").toLocaleString()),
      user,
      {
        headers: HeaderService.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }

  deleteUser(userId) {
    return this.http.delete(
      this.configuration.apiServer +
      ApiRoute.USERS_WITH_ID.replace(PATH_USER_ID, (userId.id || "").toLocaleString()),
      {
        headers: HeaderService.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }

  private errorHandler(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse.error);
  }
}
