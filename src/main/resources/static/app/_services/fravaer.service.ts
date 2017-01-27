import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

import {Fravaer} from "../_models/fravaer";
import 'rxjs/add/operator/toPromise';
import {JsonTestClass} from "../_models/json-test-class";
import {Vakt} from "../_models/vakt";

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
}
