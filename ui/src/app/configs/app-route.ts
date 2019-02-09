export enum AppRoute {

  LOGIN = 'login',
  REGISTRATION = 'registration',
  HOME = 'home',
  RESERVATION = 'reservation',
  RESERVATION_ID = '/:id',
  ADMIN_PANE = 'adminPane',

}

export namespace AppRoute {

  export enum ADMIN_PANE_CHILD {
    SOCCER_FIELD = 'soccerField',
    USER = 'user'
  }
}
