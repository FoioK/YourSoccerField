import {Injectable} from '@angular/core';
import {HttpHeaders} from '@angular/common/http';
import { TokenModel } from '../model/token-model';

@Injectable()
export class Configuration {

  authServer = 'http://127.0.0.37:8081';
  apiServer = 'http://127.0.0.37:8080/api';

  private emailRegExp: RegExp = RegExp(
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );

  static getTokenAuthorization(): HttpHeaders {
    const token: TokenModel = Configuration.getToken();

    return new HttpHeaders({
      'Authorization': token.token_type + ' ' + token.access_token
    });
  }

  static getJSONContentType(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }

  static getJSONContentTypeWithToken(): HttpHeaders {
    const token: TokenModel = Configuration.getToken();

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token.token_type + ' ' + token.access_token
    });
  }

  private static getToken(): TokenModel {
    return JSON.parse(localStorage.getItem('token'));
  }

  getEmailRegExp(): RegExp {
    return this.emailRegExp;
  }
}
