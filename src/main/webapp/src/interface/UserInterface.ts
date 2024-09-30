export interface IUser {
  id: number;
  username: string;
  email: string;
  password: string;
  oldPassword?: string;
  firstName: string;
  lastName: string;
  createdAt: string;
  updatedAt: string;
}

export interface ILogin {
  email: string;
  password: string;
}
