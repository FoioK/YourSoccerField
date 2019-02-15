import {AddressModel} from './address.model';
import {SurfaceModel} from './surface.model';
import {OpenHourModel} from './open-hour.model';

export interface SoccerFieldModel {
  id?: number;
  name: string;
  address: AddressModel;
  surface: SurfaceModel;
  width: number;
  length: number;
  price?: number;
  lighting?: boolean;
  fenced?: boolean;
  lockerRoom?: boolean;
  description?: string;
  openHour: OpenHourModel;
}
