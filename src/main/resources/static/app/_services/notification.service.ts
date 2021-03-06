/**
 * Created by Jens on 24-Jan-17.
 */
import { Injectable } from '@angular/core';
import {Http, Headers, Response} from '@angular/http';

import {Notification} from "../_models/notification";
import 'rxjs/add/operator/toPromise';
import {JsonTestClass} from "../_models/json-test-class";
import {User} from "../_models/user";
import {Fravaer} from "../_models/fravaer";
import {Observable} from "rxjs";
// import {USERS} from './mock-ansatte';

@Injectable()
export class NotificationService {
  private NotificationURL = 'http://localhost:8080/melding';

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
      .then(/*res => console.log(res)*/)
      .catch(this.handleError);
  }

  testConnect(): void {
    // console.log('ping');
    this.http
      .get('http://localhost:8080/test', { headers: this.headers })
      .toPromise()
      .then(/*res => console.log(res)*/)
      .catch(ret => this.handleError(ret));
  }

  addNotification(notification : Notification): Observable<any> {
    const URL = 'http://localhost:8080/melding/add';
    // console.log("from notificationService");
    return this.http
      .post(URL, JSON.stringify(notification), {headers: this.headers},).map((response: Response) =>
    response.json()).catch(ret => this.handleError(ret));
  }

  delete(notification : Notification): Observable<any> {
    const URL = 'http://localhost:8080/melding/delete';
    // console.log("from notificationService -delete");
    return this.http
      .post(URL, JSON.stringify(notification), {headers: this.headers},).map((response: Response) =>
        response.json()).catch(ret => this.handleError(ret));
  }

  /*getNotifications(user : User): Promise<Notification[]> {
   const URL = 'http://localhost:8080/melding/get';
   console.log("from notificationService");
   return this.http
   .post(URL, JSON.stringify(user), {headers: this.headers},)
   .toPromise()
   .then(res => res.json().data)
   .catch(this.handleError)
   }*/

  setLest(id : number) {
    const URL = 'http://localhost:8080/melding/sett/' + id;
    // console.log(URL);
    this.http.post(URL, "", { headers:this.headers },)
      .toPromise()
      .then(res => res.json().data)
      .catch(ret => this.handleError(ret));
  }

  getNotifications(user : User): Promise<Notification[]> {
    const URL = 'http://localhost:8080/melding/get';
    let returnPromise: Notification[] = [];
    let as: Object[] = [];
    // console.log(JSON.stringify(user));

    this.http.post(URL, JSON.stringify(user), {headers: this.headers}).toPromise()
      .then(response => as = (JSON.parse(response['_body'])))
      .then(() => as.forEach(
        notif => returnPromise.push(new Notification(notif['meldingId'], notif['tilBrukerId'], notif['fraBrukerId'],
          notif['overskrift'], notif['melding'], notif['tid_sendt'], notif['sett'])
        )))
      .catch(ret => this.handleError(ret));
    return Promise.resolve(returnPromise);
  }
  getNotifications1(user : User): Observable<Notification[]> {
    const URL = 'http://localhost:8080/melding/get';
    return this.http.post(URL, JSON.stringify(user), {headers: this.headers},).map((response: Response) =>
      response.json());
  }
  mapNotifFromObs(notifs:Notification[]) : Notification[] {
    return notifs.map(notif => new Notification(notif['meldingId'], notif['tilBrukerId'], notif['fraBrukerId'],
      notif['overskrift'], notif['melding'], notif['tid_sendt'], notif['sett']));
  }

  //      .then(res => res.json().data as Fravaer[])



  /*
   getUsers(): Promise<User[]> {
   return this.http.get(this.UsersURL).toPromise().then(response => response.json().data as User[]).catch(this.handleError)
   }
   */

  /*getNotification(id: number): Promise<User> {
   return this.getUsers().then(users => users.find(user => user.brukerId === id));
   }*/

  /*Brukes i profil.component.ts*/

}
