export class Vakt {
  constructor(
    public vaktId?: number,
    public vaktansvarligId?: number,
    public avdelingId?: number,
    public fraTid?: string,
    public tilTid?: string,
    public antPers?: number
  ) {}
}

