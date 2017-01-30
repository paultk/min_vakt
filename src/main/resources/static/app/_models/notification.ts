/**
 * Created by Jens on 24-Jan-17.
 */
export class Notification {
  constructor(
    public meldingId?: number,
    public tilBrukerId?: number,
    public fraBrukerId?: number,
    public overskrift?: string,
    public melding?: string,
    public tidSendt?: string,
    public sett?: boolean
  ){}
}

