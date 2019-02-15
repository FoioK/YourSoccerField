import {Injectable} from '@angular/core';
import {AUTH_SERVER} from '../../configs/configuration';
import {TokenModel} from '../../shared/models/token.model';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {SessionService} from "../services/session.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private oauthAddress = '/oauth/token';

  constructor(
    private http: HttpClient,
    private sessionService: SessionService
  ) {
  }

  getAccessToken(email: string, password: string): Observable<any> {
    return this.postUserDetails(
      AuthenticationService.getCredentialsByPassword(email, password)
    ).pipe(
      tap(token => {
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
      AuthenticationService.getCredentialsByRefreshToken(refreshToken)
    ).pipe(
      tap(token => {
          this.store(token);

          return token;
        },
        error => {
          return error;
        }
      )
    );
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

  private postUserDetails(credentials: string): Observable<TokenModel> {
    return this.http
      .post<TokenModel>(
        AUTH_SERVER + this.oauthAddress,
        credentials,
        AuthenticationService.getAuthOptions()
      )
      .pipe(catchError(AuthenticationService.errorHandler));
  }

  private static getAuthOptions() {
    return {
      headers: {
        Authorization: 'Basic ' + btoa('ysf_id:ysf_secret'),
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    };
  }

  private store(token: TokenModel) {
    localStorage.setItem('token', JSON.stringify(token));
    this.sessionService.setLogged(token != null);
  }

  private static errorHandler(errorResponse: any) {
    return throwError(errorResponse);
  }
}
