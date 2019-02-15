export interface UserModel {
  id?: number;
  code?: number;
  email: string;
  password?: string;
  active?: boolean;
  firstName: string;
  secondName: string;
  nickname: string;
  createTime?: Date;
}
