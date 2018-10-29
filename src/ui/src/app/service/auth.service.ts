import {Injectable} from '@angular/core';
import {Configuration} from "./configuration";
import {TokenModel} from "../model/token-model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private oauthAddress = "/oauth/token";

  constructor(private http: HttpClient,
              private configuration: Configuration) {
  }

  getAccessToken(email: string, password: string) {
    this.postUserDetails(email, password)
      .subscribe(token => AuthService.store(token),
        error => console.log(error))
  }

  private postUserDetails(email: string, password: string): Observable<TokenModel> {
    return this.http.post<TokenModel>(
      this.configuration.server + this.oauthAddress,
      AuthService.getCredentials(email, password),
      AuthService.getOptions()
    )
  }

  private static getCredentials(email: string, password: string): string {
    return 'username=' + email
      + '&password=' + password
      + '&grant_type=password';
  }

  private static getOptions() {
    return {
      headers: {
        'Authorization': 'Basic ' + btoa('ysf_id:ysf_secret'),
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    };
  }

  private static store(token: TokenModel) {
    localStorage.setItem('token', JSON.stringify(token));
  }
}