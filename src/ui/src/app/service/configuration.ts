import {Injectable} from "@angular/core";
import {HttpHeaders} from "@angular/common/http";

@Injectable()
export class Configuration {

  server: string = "http://localhost:8081";
  api: string = "/api";
  serverWithApiUrl: string = this.server + this.api;

  private emailRegExp: RegExp = RegExp(
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );

  getEmailRegExp(): RegExp {
    return this.emailRegExp;
  }

  static getJSONContentType(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }
}
