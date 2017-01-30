/**
 * Created by Knut on 27.01.2017.
 */

import {Component, OnInit} from "@angular/core";

import {User} from '../_models/user';
import {UserService} from '../_services/user.service';
import {FravaerService} from '../_services/fravaer.service';
import {Fravaer} from '../_models/fravaer'
import {AuthenticationService} from "../_services/authentication.service";
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
  isAdmin: boolean;

  constructor(private fravaerService:FravaerService,
              private userService:UserService,
              private authService: AuthenticationService) {
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
  onSelect(frav: Fravaer): void{
    this.selectedFravaer = frav;

  }
  slett(frav: Fravaer): void {
    this.fravaerService.delete(frav).subscribe(ret => {
      this.getFravaers();
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
    this.user = this.authService.getGlobalUser();
    // this.fravaerService.getFravaers().then(fravaers => this.fravaers = fravaers);
    this.userService.getUsers1().subscribe(ret => {
      this.users = this.userService.mapUsersFromObs(ret);
    });
    this.getFravaers();
    this.fraUser = new User();
    this.isAdmin = this.user.admin;
    console.log("here")
    console.log(this.user)
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
