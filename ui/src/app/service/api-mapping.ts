import {Injectable} from '@angular/core';

@Injectable()
export class ApiMapping {

  user_create = '/users/register';

  // @PathVariable {street}
  soccerField_findByAddressContains = '/soccerfields/byStreet/';

  // @PathVariable {soccerFieldId}
  soccerField_findById = '/soccerfields/';

  // @RequestParam {encodedObject}
  soccerField_findByCustomCriteria = '/soccerfields/advancedSearch?encodedObject=';
  soccerField_exampleTen = '/soccerfields/exampleTen';

  surfaces = '/surfaces';
}
