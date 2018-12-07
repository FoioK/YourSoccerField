export interface SoccerField {

  id?: number;
  name: string;
  addressId: number;
  surfaceId: number;
  width: number;
  length: number;
  price?: number;
  isLighting?: boolean;
  isFenced?: boolean;
  isLockerRoom?: boolean;
  description?: string;

}
