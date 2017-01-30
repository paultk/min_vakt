export class Vakt {
  constructor(
    public vakt_id?: number,
    public vaktansvarlig_id?: number,
    public avdeling_id?: number,
    public fra_tid?: string,
    public til_tid?: string,
    public ant_pers?: number
  ) {}
}

