/**Created by Trym Todalshaug on 16/01/2017.*/

import {Component, OnInit} from "@angular/core";

import {User} from '../_models/user';
import {UserService} from '../_services/user.service';
import {AuthenticationService} from "../_services/authentication.service";
import {Avdeling} from "../_models/avdeling";
import {AvdelingService} from "../_services/avdeling.service";

@Component({
  moduleId: module.id,
  selector: 'my-profil',
  templateUrl: 'profil.component.html',
  styleUrls: ['profil.component.css']
})

export class ProfilComponent implements OnInit {
  user: User = new User();
  isAdmin: boolean;
  avdeling : Avdeling = new Avdeling();

  constructor(
    private userService: UserService,
    private authService: AuthenticationService,
    private avdService : AvdelingService
  ) {}

  submit() : void {
    console.log(this.userService.updateUser(this.user));
  }

  ngOnInit(): void {
    this.user = this.userService.getEditUser();
    this.isAdmin = this.user.admin;
    this.avdService.getAvdelingen(this.user.avdelingId).then(res => this.avdeling = res);
  }
}
