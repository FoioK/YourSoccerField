export enum ApiRoutes {

  BOOKINGS = '/bookings',

  SOCCER_FIELDS = '/soccerfields',
  SOCCER_FIELDS_WITH_ID = '/soccerfields/{soccerFieldId}',
  SOCCER_FIELDS_BOOKINGS = 'soccerfields/{soccerFieldId}/bookings',
  SOCCER_FIELDS_ADVANCED_SEARCH = '/soccerfields/advancedSearch?encodedObject={encodedObject}',
  SOCCER_FIELDS_SEARCH_BY_STREET = '/soccerfields/byStreet/{street}',
  SOCCER_FIELDS_EXAMPLE_TEN = '/soccerfields/exampleTen',

  SURFACES = '/surfaces',

  USERS = '/users',
  USERS_WITH_ID = 'users/{userId}',
  USERS_BOOKINGS = 'users/{userId}/bookings',
  USERS_ADMIN_AUTHENTICATE = '/users/admin/authenticate',
}

// Soccer field parameters
export const PATH_SOCCER_FIELD_ID = '{soccerFieldId}';
export const PARAM_ENCODED_OBJECT = '{encodedObject}';
export const PATH_STREET = '{street}';

// User parameters
export const PATH_USER_ID = '{userId}';

