import {User} from "./user";
export class Shift {
  constructor(
    public user?: User,
    public fromTime?: number,
    public toTime?: number,
    public userId?: number,
    public vaktAnsvarlig?: number,
    public vaktId?: number

  ){}


}
