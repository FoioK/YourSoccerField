import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Configuration} from "./configuration";
import {User} from "../model/user";
import {ApiMapping} from "./api-mapping";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient,
              private configuration: Configuration,
              private apiMapping: ApiMapping) {

  }

  createUser(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(
      this.configuration.serverWithApiUrl + this.apiMapping.createUser,
      user,
      {
        headers: Configuration.getJSONContentType(),
        observe: 'response'
      }
    );
  }

}
