export class User {
  constructor(
    public brukerId?: number,
    public passordId?: number,
    public stillingsBeskrivelse?: string,
    public telefonNr?: number,
    public stillingsProsent?: number,
    public timelonn?: number,
    public admin?: boolean,
    public fornavn?: string,
    public etternavn?: string,
    public epost?: string,
    public avdelingId?: number,
    public plaintextPassord?: string,
    public fodselsdato?: string,
    public adresse?: string,
    public by?: string,
    public hash?: string,
    public salt?: string
  ){}
}

