import { Address } from './address';
import { Surface } from './surface';
import { OpenHour } from './open-hour';

export interface SoccerField {
  id?: number;
  name: string;
  address: Address;
  surface: Surface;
  width: number;
  length: number;
  price?: number;
  lighting?: boolean;
  fenced?: boolean;
  lockerRoom?: boolean;
  description?: string;
  openHour: OpenHour;
}
