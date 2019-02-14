export interface SearchModel {
  name?: string;
  surfaces?: Array<number>;
  paid?: boolean;
  lighting?: boolean;
  fenced?: boolean;
  lockerRoom?: boolean;
  minWidth?: number;
  maxWidth?: number;
  minLength?: number;
  maxLength?: number;
}
