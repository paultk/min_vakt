import { Injectable } from '@angular/core';
import {Http, Headers, Response} from '@angular/http';

import {Fravaer} from "../_models/fravaer";
import 'rxjs/add/operator/toPromise';
import {JsonTestClass} from "../_models/json-test-class";
import {Vakt} from "../_models/vakt";
import {Observable} from "rxjs";

@Injectable()
export class FravaerService {
  constructor(
    private http: Http
  ){}

  private headers = new Headers({'Content-Type': 'application/json', 'token': localStorage.getItem('sessionToken')});

  private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }

  testConnect2(jsonTest: JsonTestClass): void {
    this.http
      .post('http://localhost:8080/test/json', JSON.stringify(jsonTest), {headers: this.headers},)
      .toPromise()
      .then(res => console.log(res))
      .catch(this.handleError);
  }

  testConnect(): void {
    console.log('ping');
    this.http
      .get('http://localhost:8080/test',)
      .toPromise()
      .then(res => console.log(res))
      .catch(this.handleError);
  }

  registerFravaer(fravaer: Fravaer): void {
    const URL = 'http://localhost:8080/fravaer/add';
    console.log("from fravaerService");
    this.http
      .post(URL, JSON.stringify(fravaer), {headers: this.headers},)
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  getFravaerliste(): Promise<Fravaer[]> {
    const URL = 'http://localhost:8080/fravaer';
    return this.http
      .get(URL, { headers: this.headers })
      .toPromise()
      .then(res => res.json().data as Fravaer[])
      .catch(this.handleError);
  }

  getFravaers(): Promise<Fravaer[]> {
    const URL = 'http://localhost:8080/fravaer/medbrukerogvakt';
    let returnPromise: Fravaer[] = [];
    let as: Object[] = [];

    this.http.get(URL, {headers: this.headers},).toPromise()
      .then(response => as = (JSON.parse(response['_body'])))
      .then(() => as.forEach(
        frav => returnPromise.push(new Fravaer(frav['brukerVaktId'], frav['fraTid'], frav['tilTid'],
          frav['kommentar'], frav['brukerId'], frav['vaktId']))
      ))
      .catch(this.handleError);
    return Promise.resolve(returnPromise);
  }
  getFravaers1(): Observable<Fravaer[]> {
    const URL = 'http://localhost:8080/fravaer/medbrukerogvakt';
    return this.http.get(URL, {headers: this.headers},).map((response: Response) =>
      response.json());
  }
  mapFravFromObs(fravaers:Fravaer[]) : Fravaer[] {
    return fravaers.map(frav => new Fravaer(frav['brukerVaktId'], frav['fraTid'], frav['tilTid'],
      frav['kommentar'], frav['brukerId'], frav['vaktId']));
  }
  delete(frav : Fravaer): Observable<any> {
    console.log(frav);
    const URL = 'http://localhost:8080/fravaer/delete';
    return this.http
      .post(URL, JSON.stringify(frav), {headers: this.headers},).map((response: Response) =>
        response.json());
  }


  getVaktliste(): Promise<any> {
    const URL = 'http://localhost:8080/vakt/all';
    return this.http
      .get(URL, { headers: this.headers })
      .toPromise()
      .then(res => JSON.parse(res['_body']))
      //.then(res => res.json().data as Vakt[])
      .catch(this.handleError);
  }

  getFravaerByUser(id: number): Promise<Fravaer[]> {
    return this.getFravaerliste()
      .then(fravaerliste => fravaerliste.filter(fravaerliste => fravaerliste.brukerId === id));
  }

  getFravaerByVakt(id: number): Promise<Fravaer[]> {
    return this.getFravaerliste()
      .then(fravaerliste => fravaerliste.filter(fravaerliste => fravaerliste.vaktId === id));
  }

  getFravaerByDate(date: string): Promise<Fravaer[]> {
    return this.getFravaerliste()
      .then(fravaerliste => fravaerliste.filter(fravaerliste => fravaerliste.fraTid));
  }

  getVaktByDate1(date: string): Promise<any> {
    const URL = `http://localhost:8080/vakt/all/${date}`;
    return this.http.get(URL, { headers: this.headers })
      .toPromise()
      .then(res => JSON.parse(res['_body']))
      //.then(res => res.json().data as Vakt[])
      .catch(this.handleError);
  }

  getVaktByDate2(date: string): Promise<Vakt[]> {
    const URL = `http://localhost:8080/vakt/all/${date}`;
    let returnPromise: Vakt[] = [];
    let as: Object[] = [];

    this.http.get(URL, { headers: this.headers }).toPromise().then(response =>
      as = (JSON.parse(response['_body'])))
      .then(
        () =>
          as.forEach(vakt =>
            returnPromise.push(new Vakt(vakt['vaktId'], vakt['vaktansvarligId'], vakt['avdelingId'],
              vakt['fraTid'], vakt['tilTid'], vakt['antPers']
            ))

          )).catch(this.handleError);

    return Promise.resolve(returnPromise);
  }

  checkBrukerVakt(brukerId: number, vaktId: number): Boolean {
    const URL = `http://localhost:8080/brukervakt/alle`;
    let returnPromise: Fravaer[] = [];
    let as: Object[] = [];
    let brukervaktListe: Fravaer[] = [];

    this.http.get(URL, { headers: this.headers }).toPromise().then(response =>
      as = (JSON.parse(response['_body'])))
      .then(
        () =>
          as.forEach(fravaer =>
            returnPromise.push(new Fravaer(fravaer['vaktId'], fravaer['brukerId'], fravaer['brukerVaktId']
            ))

          )).catch(this.handleError);

    let response = Promise.resolve(returnPromise);
    Promise.resolve(response).then(res => brukervaktListe = res);
    if (brukervaktListe.filter(fravaer => fravaer.brukerId === brukerId) != null &&
      (brukervaktListe.filter(fravaer => fravaer.vaktId === vaktId))) {
      return true;
    }
    return false;
  }
}
