/**
 * Created by Knut on 27.01.2017.
 */

import {Component, OnInit} from "@angular/core";

import {User} from '../_models/user';
import {UserService} from '../_services/user.service';
import {FravaerService} from '../_services/fravaer.service';
import {Fravaer} from '../_models/fravaer'
/*var $ = require("jquery");*/

@Component({
  moduleId: module.id,
  selector: 'my-fravaer-info',
  templateUrl: 'fravaer-info.component.html',
  styleUrls: ['fravaer-info.component.css']
})

export class FravaerInfoComponent implements OnInit {
  user:User;
  users:User[] = [new User()];
  foundUsers:User[] = [new User()];
  fraUser:User;
  selectedFravaer : Fravaer;
  fravaer:Fravaer;
  fravaers :Fravaer[] = [ new Fravaer ];
  searchtext : String;

  constructor(private fravaerService:FravaerService,
              private userService:UserService) {
  }

  searchFravaer(): void {
    // this.getFravaer();
    this.fravaerService.getFravaers1().subscribe(ret => {
      this.fravaers = this.fravaerService.mapFravFromObs(ret);
        let funnet: Fravaer[] = new Array();
        for (let u of this.foundUsers) {
          for (let f of this.fravaers) {
            if (u.brukerId == f.brukerId) {
              funnet.push(f);
            }
          }
        }
        this.fravaers = funnet;
        console.log(funnet);
    });
  }
  getFravaers() : void {
    this.fravaerService.getFravaers1().subscribe((obs) => this.fravaers = this.fravaerService.mapFravFromObs(obs));
  }
  searchUsers(): void {
    // this.getUsers();
    this.userService.getUsers1().subscribe(ret => {
      this.users = this.userService.mapUsersFromObs(ret);
      //this.getFravaers();
      if (this.searchtext != "") {
        let textlowercase = this.searchtext.toLowerCase();
        console.log(textlowercase);
        let funnet: User[] = new Array();
        for (let u of this.users) {
          if (u.fornavn.toLowerCase().includes(textlowercase) || u.etternavn.toLowerCase().includes(textlowercase)) {
            funnet.push(u);
          }
        }
        this.foundUsers = funnet;
       // console.log(this.foundUsers);
        this.searchFravaer();
      }
      else {
        this.getFravaers();
      }
    });
    /* else {
     this.getUsers();
     }*/
  }

  ngOnInit():void {
    this.user = this.userService.getCurrentUser();
    // this.fravaerService.getFravaers().then(fravaers => this.fravaers = fravaers);
    this.userService.getUsers1().subscribe(ret => {
      this.users = this.userService.mapUsersFromObs(ret);
    });
    this.getFravaers();
    this.fraUser = new User();
  }

  selectUser(id : number){
    for (let u of this.users) {
      if (u.brukerId == id) {
        this.fraUser = u;
        // console.log(u);
      }
    }
  }
}
