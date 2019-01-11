export interface TokenModel {

  id: number;
  code: number;
  email: string;
  isActive: boolean;
  access_token: string;
  refresh_token: string;
  token_type: string;
  createTime: Date;
}
