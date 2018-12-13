import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Configuration} from './configuration';
import {ApiMapping} from './api-mapping';
import {Observable} from 'rxjs';
import {SoccerField} from '../model/SoccerField';

@Injectable({
  providedIn: 'root'
})
export class SoccerFieldService {

  constructor(private http: HttpClient,
              private configuration: Configuration,
              private apiMapping: ApiMapping) {

  }

  findByAddressContains(street: string): Observable<Array<SoccerField>> {
    return this.http
      .get<Array<SoccerField>>(
        this.configuration.apiServer +
        this.apiMapping.soccerField_findByAddressContains +
        street,
        {
          headers: Configuration.getJSONContentType()
        });
  }
}
