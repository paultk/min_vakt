import { Injectable } from '@angular/core';
import {Http, Headers, Response} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import  "rxjs/Rx";
import {Observable} from "rxjs";

import {Authentication} from "../_models/authentication";
import {User} from "../_models/user";

@Injectable()
export class AuthenticationService {
  constructor(private http: Http) {}

  private headers = new Headers({'Content-Type': 'application/json', 'token': localStorage.getItem('sessionToken')});

  private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }

  login(auth: Authentication) {
    const URL = 'http://localhost:8080/login';
    return this.http
      .post(URL, JSON.stringify(auth), {headers: this.headers})
      .map((response: Response) => {
        let re = /@/gi;
        let str = auth.username.replace(re, "&at");
        re = /\./gi;
        str = str.replace(re, "&dot");
        let responseToken = response.text();
        if (responseToken) {
          localStorage.setItem('sessionToken', responseToken);
          localStorage.setItem('currentUserEmail', str);
        }
      });
  }

  logout() {
    localStorage.removeItem('sessionToken');
    localStorage.removeItem('currentUserEmail');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('rememberMe');
    sessionStorage.removeItem('currentUser');
  }

  setCurrentUser(email: string): Observable<User[]> {
    const URL = `http://localhost:8080/bruker/epost/${email}`;

    return this.http.get(URL, {headers: this.headers},).map((response: Response) =>
      response.json());
  }

  getGlobalUser(): User {
    let obj: Object[];

    if (localStorage.getItem('rememberMe') == "true") {
      obj = JSON.parse(localStorage.getItem('currentUser'));
    } else {
      obj = JSON.parse(sessionStorage.getItem('currentUser'));
    }

    return new User(obj['brukerId'], obj['passordId'], obj['stillingsBeskrivelse'], obj['telefonNr'],
      obj['stillingsProsent'], obj['timelonn'], obj['admin'], obj['fornavn'], obj['etternavn'],
      obj['epost'], obj['avdelingId'], obj['plaintextPassord'], obj['fodselsdato'], obj['adresse'],
      obj['by'], obj['hash'], obj['salt']);
  }
}
