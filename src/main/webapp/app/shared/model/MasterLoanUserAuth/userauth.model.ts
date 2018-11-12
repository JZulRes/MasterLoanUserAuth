export interface IUserauth {
  id?: number;
  cedulaCustomer?: number;
  typeIdCustomer?: string;
  userName?: string;
  password?: string;
}

export class Userauth implements IUserauth {
  constructor(
    public id?: number,
    public cedulaCustomer?: number,
    public typeIdCustomer?: string,
    public userName?: string,
    public password?: string
  ) {}
}
