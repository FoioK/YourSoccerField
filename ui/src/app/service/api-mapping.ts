import {Injectable} from '@angular/core';
import { SoccerField } from '../model/soccer-field';

@Injectable()
export class ApiMapping {

  user_create = '/users/register';
  user_adminPane_authenticate = '/users/admin/authenticate';

  soccerField_findAll = '/soccerfields';

  // @PathVariable {street}
  soccerField_findByAddressContains = '/soccerfields/byStreet/';

  // @PathVariable {soccerFieldId}
  soccerField_findById = '/soccerfields/';

  // soccerField_findById + @PathVariable {soccerFieldId} + soccerField_reservationsById
  soccerField_reservationsById = '/bookings/';

  // @RequestParam {encodedObject}
  soccerField_findByCustomCriteria = '/soccerfields/advancedSearch?encodedObject=';
  soccerField_exampleTen = '/soccerfields/exampleTen';

  surfaces = '/surfaces';
}
