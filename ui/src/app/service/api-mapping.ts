import {Injectable} from '@angular/core';

@Injectable()
export class ApiMapping {

  user_create = '/users/register';

  // @PathVariable {street}
  soccerField_findByAddressContains = '/soccerfields/byStreet/';
  // @RequestParam {encodedObject}
  soccerField_findByCustomCriteria = '/soccerfields/advancedSearch?encodedObject=';
  soccerField_exampleTen = '/soccerfields/exampleTen';

  surfaces = '/surfaces'
}
