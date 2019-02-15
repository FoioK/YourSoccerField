import {environment} from "../../environments/environment";

export const API_SERVER = environment.apiServer;
export const AUTH_SERVER = environment.authServer;

export const EMAIL_REG_EXP: RegExp = RegExp(
  /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
);
