import {Injectable} from '@angular/core';
import {TokenModel} from '../shared/models/token-model';
import {Router} from "@angular/router";

@Injectable()
export class Configuration {

  authServer = 'http://127.0.0.37:8081';
  apiServer = 'http://127.0.0.37:8080/api';

  constructor(private router: Router) {

  }

  // TODO do przeniesienia gdzieś
  private emailRegExp: RegExp = RegExp(
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );

  // TODO do przeniesienia gdzieś
  public static getToken(): TokenModel {
    return JSON.parse(localStorage.getItem('token'));
  }

  getEmailRegExp(): RegExp {
    return this.emailRegExp;
  }
}
