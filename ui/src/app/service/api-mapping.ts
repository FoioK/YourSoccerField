import {Injectable} from '@angular/core';
import { SoccerField } from '../model/soccer-field';

@Injectable()
export class ApiMapping {

  user_create = '/users';
  user_adminPane_authenticate = '/users/admin/authenticate';
  user_byId = '/users/';

  soccerField_findAll = '/soccerfields';

  // @PathVariable {street}
  soccerField_findByAddressContains = '/soccerfields/byStreet/';

  // @PathVariable {soccerFieldId}
  soccerField_findById = '/soccerfields/';

  // @Body {reservation}
  booking_create = '/bookings';

  // soccerField_findById + @PathVariable {soccerFieldId} + soccerField_reservationsById
  // TO DO

  // @RequestParam {encodedObject}
  soccerField_findByCustomCriteria = '/soccerfields/advancedSearch?encodedObject=';
  soccerField_exampleTen = '/soccerfields/exampleTen';

  surfaces = '/surfaces';
}
