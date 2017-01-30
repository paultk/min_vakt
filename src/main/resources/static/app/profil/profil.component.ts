/**Created by Trym Todalshaug on 16/01/2017.*/

import {Component, OnInit} from "@angular/core";

import {User} from '../_models/user';
import {UserService} from '../_services/user.service';
import {AuthenticationService} from "../_services/authentication.service";
import {Avdeling} from "../_models/avdeling";
import {AvdelingService} from "../_services/avdeling.service";
import {Stilling} from "../_models/stilling";

@Component({
  moduleId: module.id,
  selector: 'my-profil',
  templateUrl: 'profil.component.html',
  styleUrls: ['profil.component.css']
})

export class ProfilComponent implements OnInit {
  private user: User = new User();
  private isAdmin: boolean;
  private avdelinger : Avdeling[] = [ new Avdeling() ];
  private stillinger : Stilling[] = [ new Stilling("Assistent"), new Stilling("Helsefagarbeider"), new Stilling("Sykepleier") ];
  private pass1 : string;
  private pass2 : string;

  constructor(
    private userService: UserService,
    private authService: AuthenticationService,
    private avdService : AvdelingService
  ) {}

  submit() : void {
    // console.log(this.user);
    // if (this.pass1 != "" && this.pass2 != "" && this.pass1 === this.pass2) {
    //   // this.user.plaintextPassord=this.pass1;
    //   //TODO denne skal oppdatere passord
    // }
    this.userService.updateUser(this.user).subscribe(ret => console.log(ret));
  }

  ngOnInit(): void {
    this.user = this.userService.getEditUser();
    this.isAdmin = this.user.admin;
    this.avdService.getAvdelinger().then(ret => this.avdelinger = ret);
  }
}
