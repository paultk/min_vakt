import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';

import {User} from "../_models/user";
import 'rxjs/add/operator/toPromise';
import {JsonTestClass} from "../_models/json-test-class";
import  "rxjs/Rx";
import {Observable} from "rxjs";

@Injectable()
export class UserService {

  private UsersURL = 'http://localhost:8080/bruker/alle';

  constructor(private http: Http) {
  }

  private currentUser : User;
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
      .get('http://localhost:8080/test', {headers: this.headers},)
      .toPromise()
      .then(res => console.log(res))
      .catch(this.handleError);
  }

  addUser(user: User): void {
    const URL = 'http://localhost:8080/bruker/add';
    console.log("from userService");
    this.http
      .post(URL, JSON.stringify(user), {headers: this.headers},)
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  getUsers1(): Observable<User[]> {
    return this.http.get(this.UsersURL, {headers: this.headers},).map((response: Response) =>
      response.json());
  }

  getUsers(): Promise<User[]> {
    const URL = 'http://localhost:8080/bruker/alle';
    let returnPromise: User[] = [];
    let as: Object[] = [];

    this.http.get(URL, {headers: this.headers},).toPromise()
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

  getCurrentUser(): User {
    /*let ret : User = JSON.parse(localStorage.getItem('currentUser'));
    console.log(ret);
    return ret;*/
    return new User(49, 1, 'helsefagebeider', 41414141, 100, 200, false, 'Mr. Nice', 'Johnson', 'narco@minvakt.no', 1, 'Admin@@@', '01.01.2016', 'veigata 5', 'Trondheim');
  }

}
