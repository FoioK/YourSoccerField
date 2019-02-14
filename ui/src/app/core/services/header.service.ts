import {Injectable} from "@angular/core";
import {HttpHeaders} from "@angular/common/http";
import {TokenModel} from "../../shared/models/token.model";
import {SessionService} from "./session.service";

@Injectable()
export class HeaderService {

  constructor() {

  }

  static JSONContentType(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }

  static JSONContentTypeWithToken(sessionService: SessionService): HttpHeaders {
    const token: TokenModel = sessionService.getToken();

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token.token_type + ' ' + token.access_token
    });
  }

  static tokenWithoutContentType(sessionService: SessionService): HttpHeaders {
    const token: TokenModel = sessionService.getToken();

    return new HttpHeaders({
      'Authorization': token.token_type + ' ' + token.access_token
    });
  }

}
