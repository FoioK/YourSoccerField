import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Configuration} from '../../../configs/configuration';
import {Observable} from 'rxjs';
import {SoccerField} from '../../../shared/models/soccer-field';
import {SearchModel} from '../../../shared/models/search-model';
import {Surface} from '../../../shared/models/surface';
import {HeaderService} from "../../services/header.service";
import {ApiRoute, PARAM_ENCODED_OBJECT, PATH_SOCCER_FIELD_ID, PATH_STREET} from "../api.route";

@Injectable({
  providedIn: 'root'
})
export class SoccerFieldService {
  constructor(
    private http: HttpClient,
    private configuration: Configuration,
  ) {
  }

  findAll(): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer + ApiRoute.SOCCER_FIELDS,
      {
        headers: HeaderService.getJSONContentTypeWithToken()
      }
    );
  }

  findByAddressContains(street: string): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer +
      ApiRoute.SOCCER_FIELDS_SEARCH_BY_STREET.replace(PATH_STREET, street),
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
      ApiRoute.SOCCER_FIELDS_ADVANCED_SEARCH.replace(PARAM_ENCODED_OBJECT, btoa(stringJson)),
      {
        headers: HeaderService.getJSONContentType()
      }
    );
  }

  getExampleTen(): Observable<Array<SoccerField>> {
    return this.http.get<Array<SoccerField>>(
      this.configuration.apiServer + ApiRoute.SOCCER_FIELDS_EXAMPLE_TEN,
      {
        headers: HeaderService.getJSONContentType()
      }
    );
  }

  getAllSurfaces(): Observable<Array<Surface>> {
    return this.http.get<Array<Surface>>(
      this.configuration.apiServer + ApiRoute.SURFACES,
      {
        headers: HeaderService.getJSONContentType()
      }
    );
  }

  getSoccerfieldById(id: number): Observable<SoccerField> {
    return this.http.get<SoccerField>(
      this.configuration.apiServer +
      ApiRoute.SOCCER_FIELDS_WITH_ID.replace(PATH_SOCCER_FIELD_ID, (id || "").toLocaleString()),
      {
        headers: HeaderService.getJSONContentTypeWithToken()
      }
    );
  }

  updateSoccerField(soccerField: SoccerField) {
    return this.http.put(
      this.configuration.apiServer +
      ApiRoute.SOCCER_FIELDS_WITH_ID.replace(
        PATH_SOCCER_FIELD_ID,
        (soccerField.id || "").toLocaleString()
      ),
      soccerField,
      {
        headers: HeaderService.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }

  deleteSoccerField(soccerFieldId) {
    return this.http.delete(
      this.configuration.apiServer +
      ApiRoute.SOCCER_FIELDS_WITH_ID.replace(
        PATH_SOCCER_FIELD_ID,
        (soccerFieldId || "").toLocaleString()
      ),
      {
        headers: HeaderService.getJSONContentTypeWithToken(),
        observe: "response"
      }
    )
  }
}
