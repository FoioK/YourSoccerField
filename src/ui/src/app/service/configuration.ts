import {Injectable} from "@angular/core";

@Injectable()
export class Configuration {

  server: string = "http://localhost:8081";
  api: string = "/api";
  serverWithApiUrl: string = this.server + this.api;

}
