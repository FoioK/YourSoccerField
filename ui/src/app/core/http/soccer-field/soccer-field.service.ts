import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Configuration} from '../../../configs/configuration';
import {ApiMapping} from '../../../configs/api-mapping';
import {Observable} from 'rxjs';
import {SoccerField} from '../../../shared/models/soccer-field';
import {SearchModel} from '../../../shared/models/search-model';
import {Surface} from '../../../shared/models/surface';
import {HeaderService} from "../../services/header.service";

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
      this.configuration.apiServer + this.apiMapping.soccerField_findAll,
      {
        headers: HeaderService.getJSONContentTypeWithToken()
      }
    );
  }

  findByAddressContains(street: string): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer +
      this.apiMapping.soccerField_findByAddressContains +
      street,
      {
        headers: HeaderService.getJSONContentType()
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
        headers: HeaderService.getJSONContentType()
      }
    );
  }

  getExampleTen(): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer + this.apiMapping.soccerField_exampleTen,
      {
        headers: HeaderService.getJSONContentType()
      }
    );
  }

  getAllSurfaces(): Observable<Array<Surface>> {
    return this.http.get<Array<Surface>>(
      this.configuration.apiServer + this.apiMapping.surfaces,
      {
        headers: HeaderService.getJSONContentType()
      }
    );
  }

  getSoccerfieldById(id: number): Observable<SoccerField> {
    return this.http.get<SoccerField>(
      this.configuration.apiServer + this.apiMapping.soccerField_findById + id,
      {
        headers: HeaderService.getJSONContentTypeWithToken()
      }
    );
  }

  updateSoccerField(soccerField: SoccerField) {
    return this.http.put(
      this.configuration.apiServer + this.apiMapping.soccerField_findById + soccerField.id,
      soccerField,
      {
        headers: HeaderService.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }

  deleteSoccerField(soccerFieldId) {
    return this.http.delete(
      this.configuration.apiServer + this.apiMapping.soccerField_findById + soccerFieldId,
      {
        headers: HeaderService.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }
}
