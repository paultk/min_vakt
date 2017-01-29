/**
 * Created by Trym Todalshaug on 23/01/2017.
 */

import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Avdeling} from "../_models/avdeling";

@Injectable()
export class AvdelingService {

  constructor(
    private http: Http
  ){}

  private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }

  private headers = new Headers({'Content-Type': 'application/json', 'token': localStorage.getItem('sessionToken')});

  getAvdeling(): Promise<any> {
    const URL = 'http://localhost:8080/avdeling/all';

    return this.http
      .get(URL, { headers: this.headers })
      .toPromise()
      .then(res => JSON.parse(res['_body']))
      //.then(res => res.json().data as Avdeling[])
      .catch(this.handleError);
  }
  getAvdelingen(id: number): Promise<Avdeling> {
    return this.getAvdelinger().then(ret => ret.find(ret => ret.avdelingId === id));
  }

  getAvdelinger(): Promise<Avdeling[]> {
    const URL = `http://localhost:8080/avdeling/all`;
    let returnPromise: Avdeling[] = [];
    let as: Object[] = [];

    this.http.get(URL, { headers: this.headers }).toPromise()
      .then(response => as = (JSON.parse(response['_body'])))
      .then(() => as.forEach(
        avdeling => returnPromise.push(new Avdeling(avdeling['avdelingId'], avdeling['navn'])
        )))
      .catch(this.handleError);
    return Promise.resolve(returnPromise);
  }
}
