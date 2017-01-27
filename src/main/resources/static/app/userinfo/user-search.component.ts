/**
 * Created by Trym Todalshaug on 25/01/2017.
 */

import {Component, OnInit} from '@angular/core';
import {Observable}        from 'rxjs/Observable';
import {Subject}           from 'rxjs/Subject';

import {UserSearchService} from '../_services/user-search.service';
import {User} from '../_models/user';

@Component({
  moduleId: module.id,
  selector: 'user-search',
  templateUrl: 'user-search.component.html',
  styleUrls: ['user-search.component.css'],
  providers: [UserSearchService]
})

export class UserSearchcomponent implements OnInit {
  users: Observable<User[]>;
  private searchTerms = new Subject<string>();

  constructor(
    private userSearchService: UserSearchService,
  ){}

  //Push a search term into the observable stream
  search(term: string): void {
      this.searchTerms.next(term);
  }

  ngOnInit(): void {
      this.users = this.searchTerms
        .debounceTime(300)
        .distinctUntilChanged()
        .switchMap(term => term
        ? this.userSearchService.search(term)
        :Observable.of<User[]>([]))
        .catch(error => {
          //TODO: real error handling
          console.log(error);
          return Observable.of<User[]>([]);
        });
  }

  gotoDetail(user: User): void {
      let link = ['/detail', user.brukerId]
  }
}
