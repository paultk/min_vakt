import { Injectable } from '@angular/core';
import {Http, Headers, Response} from '@angular/http';

import {User} from "../_models/user";
import 'rxjs/add/operator/toPromise';
import  "rxjs/Rx";
import {Observable} from "rxjs";

@Injectable()
export class UserService {
  private editUser: User;

  private UsersURL = 'http://localhost:8080/bruker/alle';
  constructor(
    private http: Http
  ){}

  private headers = new Headers({'Content-Type': 'application/json', 'token': localStorage.getItem('sessionToken')});

  private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.reject(error.message || error);
  }

  testConnect2() {}

  getEditUser() : User {
    return this.editUser;
  }
  setEditUser(user:User):void {
    this.editUser = user;
  }

  addUser(user: User): void {
    const URL = 'http://localhost:8080/bruker/add';
    this.http
      .post(URL, JSON.stringify(user), {headers: this.headers},)
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  getUsers1(): Observable<User[]> {
    return this.http.get(this.UsersURL, {headers: this.headers}).map((response: Response) =>
      response.json());
  }

  mapUsersFromObs(inn : User[]) : User[] {
    return inn.map(usr => new User(usr['brukerId'], usr['passordId'], usr['stillingsBeskrivelse'],
      usr['telefonNr'], usr['stillingsProsent'], usr['timelonn'], usr['admin'], usr['fornavn'],
      usr['etternavn'], usr['epost'], usr['avdelingId'], "Admin@@@", "", "", "", usr['hash'], usr['salt']));
  }


  getUsers(): Promise<User[]> {
    const URL = 'http://localhost:8080/bruker/alle';
    let returnPromise: User[] = [];
    let as: Object[] = [];

    this.http.get(URL, {headers: this.headers}).toPromise()
      .then(response => as = (JSON.parse(response['_body'])))
      .then(() => as.forEach(
        user => returnPromise.push(new User(user['brukerId'], user['passordId'], user['stillingsBeskrivelse'], user['telefonNr'],
          user['stillingsProsent'], user['timelonn'], user['admin'], user['fornavn'], user['etternavn'],
          user['epost'], user['avdelingId'], user['plaintextPassord'], user['fodselsdato'], user['adresse'],
          user['by'], user['hash'], user['salt']))
      ))
      .catch(this.handleError);
    return Promise.resolve(returnPromise);
  }



  resetUserPassword(user: User): void {
    const URL = 'http://localhost:8080/bruker/glemtpassord';

    this.http
      .post(URL, JSON.stringify(user), {headers: this.headers},)
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  getUser(id: number): Promise<User> {
    return this.getUsers().then(users => users.find(user => user.brukerId === id));
  }



  getUserByEmail(email: string): Promise<User[]> {
    const URL = `http://localhost:8080/bruker/epost/${email}`;
    let returnPromise: User[] = [];
    let as: Object[] = [];

    this.http.get(URL, {headers: this.headers},).toPromise()
      .then(response => as = (JSON.parse(response['_body'])))
      .then(() => as.forEach(
        user => returnPromise.push(new User(user['brukerId'], user['passordId'], user['stillingsBeskrivelse'], user['telefonNr'],
          user['stillingsProsent'], user['timelonn'], user['admin'], user['fornavn'], user['etternavn'],
          user['epost'], user['avdelingId'], user['plaintextPassord'], user['fodselsdato'], user['adresse'],
          user['by'], user['hash'], user['salt']))
      )).catch(this.handleError);
    return Promise.resolve(returnPromise);

  }

  delete(user : User): Observable<any> {
    console.log(user);
    const URL = 'http://localhost:8080/bruker/delete';
    return this.http
      .post(URL, JSON.stringify(user), {headers: this.headers},).map((response: Response) =>
        response.json());
  }



  /*Brukes i profil.component.ts*/
  getCurrentUser(): User {
    return new User(49, 1, 'Helsefagebeider', 41414141, 100, 200, false, 'Mr. Nice', 'Johnson', 'narco@minvakt.no', 1, 'Admin@@@', '01.01.2016', 'veigata 5', 'Trondheim');
  }
}
