import {Address} from "./address";
import {Surface} from "./surface";
import {OpenHour} from "./open-hour";

export interface SoccerField {

  id?: number;
  name: string;
  address: Address;
  surface: Surface;
  width: number;
  length: number;
  price?: number;
  isLighting?: boolean;
  isFenced?: boolean;
  isLockerRoom?: boolean;
  description?: string;
  openHour: OpenHour;

}
