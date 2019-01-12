import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Configuration} from './configuration';
import {ApiMapping} from './api-mapping';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private apiMapping: ApiMapping
  ) {

  }

  private isLoggedSubject = new BehaviorSubject<boolean>(false);

  isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  setLogged(isLogged: boolean) {
    this.isLoggedSubject.next(isLogged);
  }

  logOut() {
    localStorage.removeItem('token');
    this.setLogged(false);
  }

  adminPaneAuthenticate() {
    this.http.get(
      this.configuration.apiServer + this.apiMapping.user_adminPane_authenticate,
      {headers: Configuration.getTokenAuthorization()}
    );
  }

}
