/**Created by Trym Todalshaug on 16/01/2017.*/

import {Component, OnInit} from "@angular/core";

import {User} from '../_models/user';
import {UserService} from '../_services/user.service';
import {AuthenticationService} from "../_services/authentication.service";
import {Avdeling} from "../_models/avdeling";
import {AvdelingService} from "../_services/avdeling.service";
import {Stilling} from "../_models/stilling";
import {Authentication} from "../_models/authentication";
import {LoginComponent} from "../login/login.component";
import {Router} from "@angular/router";

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
  private pass1 : string = "";
  private pass2 : string = "";

  constructor(
    private userService: UserService,
    private authService: AuthenticationService,
    private avdService : AvdelingService
  ) {}

  submit() : void {
    this.userService.updateUser(this.user).subscribe(ret => {
      this.authService.updateGlobalUser();
    });
  }

  changePass() : void {
    if (this.pass1 != "" && this.pass2 != "" && this.pass1 == this.pass2) {
      this.user.plaintextPassord=this.pass1;
      this.userService.updatePassword(this.user).subscribe(ret => {
        this.authService.logout();
      });
    }
  }

  ngOnInit(): void {
    this.user = this.userService.getEditUser();
    // this.user = this.authService.getGlobalUser();
    this.isAdmin = this.authService.getGlobalUser().admin;
    this.avdService.getAvdelinger().then(ret => this.avdelinger = ret);
  }
}
