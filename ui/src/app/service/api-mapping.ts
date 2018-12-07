import {Injectable} from '@angular/core';

@Injectable()
export class ApiMapping {

  user_create = '/register';

  // @PathVariable {street}
  soccerField_findByAddressContains = '/soccerfields/';
}
