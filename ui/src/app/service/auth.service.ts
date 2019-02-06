import { Injectable } from '@angular/core';
import { Configuration } from './configuration';
import { TokenModel } from '../model/token-model';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError, of, empty } from 'rxjs';
import { UserService } from './user.service';
import { catchError, tap } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private oauthAddress = '/oauth/token';
  private jwtHelper = new JwtHelperService();

  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private userService: UserService
  ) {}

  getAccessToken(email: string, password: string): Observable<any> {
    return this.postUserDetails(
      AuthService.getCredentialsByPassword(email, password)
    ).pipe(
      tap(
        token => {
          this.store(token);
          return token;
        },
        error => {
          return error;
        }
      )
    );
  }

  refreshAccessToken(refreshToken: string) {
    this.postUserDetails(
      AuthService.getCredentialsByRefreshToken(refreshToken)
    );
  }

  private postUserDetails(credentials: string): Observable<TokenModel> {
    return this.http
      .post<TokenModel>(
        this.configuration.authServer + this.oauthAddress,
        credentials,
        AuthService.getOptions()
      )
      .pipe(catchError(AuthService.errorHandler));
  }

  private static getCredentialsByPassword(
    email: string,
    password: string
  ): string {
    return (
      'username=' + email + '&password=' + password + '&grant_type=password'
    );
  }

  private static getCredentialsByRefreshToken(refreshToken: string): string {
    return 'refresh_token=' + refreshToken + '&grant_type=refresh_token';
  }

  private static getOptions() {
    return {
      headers: {
        Authorization: 'Basic ' + btoa('ysf_id:ysf_secret'),
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    };
  }

  private store(token: TokenModel) {
    localStorage.setItem('token', JSON.stringify(token));
    this.userService.setLogged(token != null);
  }

  private static errorHandler(errorResponse: any) {
    return throwError(errorResponse);
  }
}
