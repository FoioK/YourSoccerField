import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Configuration} from './configuration';
import {ApiMapping} from './api-mapping';
import {Observable} from 'rxjs';
import {SoccerField} from '../model/soccer-field';
import {SearchModel} from '../model/search-model';
import {Surface} from '../model/surface';

@Injectable({
  providedIn: 'root'
})
export class SoccerFieldService {
  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private apiMapping: ApiMapping
  ) {
  }

  findAll(): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer +
      this.apiMapping.soccerField_findAll,
      {
        headers: Configuration.getJSONContentTypeWithToken()
      }
    )
  }

  findByAddressContains(street: string): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer +
      this.apiMapping.soccerField_findByAddressContains +
      street,
      {
        headers: Configuration.getJSONContentType()
      }
    );
  }

  findByCustomCriteria(
    searchModel: SearchModel
  ): Observable<Array<SoccerField>> {
    const stringJson: string = JSON.stringify(searchModel);

    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer +
      this.apiMapping.soccerField_findByCustomCriteria +
      btoa(stringJson),
      {
        headers: Configuration.getJSONContentType()
      }
    );
  }

  getExampleTen(): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer + this.apiMapping.soccerField_exampleTen,
      {
        headers: Configuration.getJSONContentType()
      }
    );
  }

  getAllSurfaces(): Observable<Array<Surface>> {
    return this.http.get<Array<Surface>>(
      this.configuration.apiServer + this.apiMapping.surfaces,
      {
        headers: Configuration.getJSONContentType()
      }
    );
  }

  updateSoccerField(soccerField: SoccerField) {
    return this.http.put(
      this.configuration.apiServer + this.apiMapping.soccerField_findById + soccerField.id,
      soccerField,
      {
        headers: Configuration.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }

  deleteSoccerField(soccerFieldId) {
    return this.http.delete(
      this.configuration.apiServer + this.apiMapping.soccerField_findById + soccerFieldId,
      {
        headers: Configuration.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }
}
