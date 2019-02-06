import {Injectable} from '@angular/core';

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

  // @RequestParam {encodedObject}
  soccerField_findByCustomCriteria = '/soccerfields/advancedSearch?encodedObject=';
  soccerField_exampleTen = '/soccerfields/exampleTen';

  surfaces = '/surfaces';
}
