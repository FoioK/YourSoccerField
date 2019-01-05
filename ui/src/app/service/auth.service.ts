import { Injectable } from "@angular/core";
import { Configuration } from "./configuration";
import { TokenModel } from '../model/token-model';
import { HttpClient } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { JwtHelperService } from "@auth0/angular-jwt";
import { UserService } from "./user.service";

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
    this.postUserDetails(email, password).subscribe(
      token => this.store(token),
      error => console.log(error)
    );
  }

  private errorHandler(errorResponse: any) {
    return throwError(errorResponse);
  }

  private postUserDetails(
    email: string,
    password: string
  ): Observable<TokenModel> {
    return this.http
      .post<TokenModel>(
        this.configuration.authServer + this.oauthAddress,
        AuthService.getCredentials(email, password),
        AuthService.getOptions()
      )
      .pipe(catchError(this.errorHandler));
  }

  private static getCredentials(email: string, password: string): string {
    return (
      "username=" + email + "&password=" + password + "&grant_type=password"
    );
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
}
