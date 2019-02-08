import {Injectable} from "@angular/core";
import {HttpHeaders} from "@angular/common/http";
import {TokenModel} from "../../shared/models/token-model";
import {AppRoute} from "../../configs/app-route";
import {Configuration} from "../../configs/configuration";
import {Router} from "@angular/router";

@Injectable()
export class HeaderService {

  constructor(private router: Router) {

  }

  getTokenAuthorization(): HttpHeaders {
    const token: TokenModel = Configuration.getToken();

    if (token == null) {
      this.router.navigateByUrl(AppRoute.LOGIN);
    }

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

}
