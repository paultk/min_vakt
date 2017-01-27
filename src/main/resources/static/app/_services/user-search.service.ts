/**
 * Created by Trym Todalshaug on 25/01/2017.
 */

import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';

import {User} from '../_models/user'

@Injectable()
export class UserSearchService {

  constructor(private http: Http){

  }

  search(term: string): Observable<User[]> {
    return this.http
      .get(`app/users/?fornavn=${term}`)
      .map((r: Response) => r.json().data as User[]);
  }
}
