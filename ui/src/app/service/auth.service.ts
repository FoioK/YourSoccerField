import {Injectable} from '@angular/core';
import {Configuration} from './configuration';
import {TokenModel} from '../model/token-model';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {UserService} from './user.service';
import {catchError} from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: "root"
})
export class AuthService {
  private oauthAddress = "/oauth/token";
  private jwtHelper = new JwtHelperService();

  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private userService: UserService
  ) {}

  getAccessToken(email: string, password: string) {
    this.postUserDetails(AuthService.getCredentialsByPassword(email, password))
      .subscribe(token => this.store(token),
        error => console.log(error)
      );
  }

  refreshAccessToken(refreshToken: string) {
    this.postUserDetails(AuthService.getCredentialsByRefreshToken(refreshToken))
  }

  private postUserDetails(credentials: string): Observable<TokenModel> {
    return this.http.post<TokenModel>(
      this.configuration.authServer + this.oauthAddress,
      credentials,
      AuthService.getOptions()
      ).pipe(catchError(AuthService.errorHandler));
  }


  private static getCredentialsByPassword(email: string, password: string): string {
    return (
      "username=" + email + "&password=" + password + "&grant_type=password"
    );
  }

  private static getCredentialsByRefreshToken(refreshToken: string): string {
    return 'refresh_token=' + refreshToken
      + '&grant_type=refresh_token';
  }


  private static getOptions() {
    return {
      headers: {
        Authorization: "Basic " + btoa("ysf_id:ysf_secret"),
        "Content-Type": "application/x-www-form-urlencoded"
      }
    };
  }

  private store(token: TokenModel) {
    localStorage.setItem("token", JSON.stringify(token));
    this.userService.setLogged(token != null);
  }

  public isAuthenticated(): boolean {
    const token: TokenModel = JSON.parse(localStorage.getItem("token"));
    return token != null && !this.jwtHelper.isTokenExpired(token.access_token);
  }

  private static errorHandler(errorResponse: any) {
    return throwError(errorResponse);
  }
}
