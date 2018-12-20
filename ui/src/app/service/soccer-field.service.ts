import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Configuration} from './configuration';
import {ApiMapping} from './api-mapping';
import {Observable} from 'rxjs';
import {SoccerField} from '../model/soccer-field';
import {SearchModel} from "../model/search-model";

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
        }
      );
  }

  findByCustomCriteria(searchModel: SearchModel): Observable<Array<SoccerField>> {
    const stringJson: string = JSON.stringify(searchModel);

    return this.http
      .get<Array<SoccerField>>(
        this.configuration.apiServer +
        this.apiMapping.soccerField_findByCustomCriteria +
        btoa(stringJson),
        {
          headers: Configuration.getJSONContentType()
        }
      )
  }
}
