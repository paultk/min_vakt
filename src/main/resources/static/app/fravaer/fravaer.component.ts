import {Component, OnInit} from '@angular/core';

import { Fravaer } from '../_models/fravaer';
import {Vakt} from '../_models/vakt';
import {FravaerService} from "../_services/fravaer.service";
import {User} from "../_models/user";
import {UserService} from "../_services/user.service";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'my-fravaer',
  templateUrl: 'fravaer.component.html',
  styleUrls: [ 'fravaer.component.css' ]
})

export class FravaerComponent implements OnInit {

  constructor(
    private fravaerService: FravaerService,
    private userService: UserService,
    private router: Router
  ) {}

  model = new Fravaer();

  users: User[] = [];
  brukervaktListe: Fravaer[] = [];

  date: {year: number, month: number};

  timeObject: any;
  fromTime = {hour: 6, minute: 0};
  toTime = {hour: 12, minute: 0};

  vaktliste: Vakt[];
  selectedVakt: Vakt;
  selectedUser: User;

  completeDateFrom = [""];
  completeDateTo = [""];

  submitted = false;

  onSelect(vakt: Vakt): void {
    this.selectedVakt = vakt;
  }

  refreshVakter(newValue: any): void {

    this.timeObject = newValue;

    this.completeDateFrom[0] = this.timeObject.year.toString();
    this.completeDateFrom[1] = this.timeObject.month.toString();
    this.completeDateFrom[2] = this.timeObject.day.toString();
    this.completeDateFrom[3] = this.fromTime.hour.toString();
    this.completeDateFrom[4] = this.fromTime.minute.toString();

    this.completeDateTo[0] = this.timeObject.year.toString();
    this.completeDateTo[1] = this.timeObject.month.toString();
    this.completeDateTo[2] = this.timeObject.day.toString();
    this.completeDateTo[3] = this.toTime.hour.toString();
    this.completeDateTo[4] = this.toTime.minute.toString();

    for (let attr of this.completeDateFrom) {
      if (attr.length == 1) {
        this.completeDateFrom[this.completeDateFrom.indexOf(attr)] = "0" + attr;
      }
    }

    for (let attr of this.completeDateTo) {
      if (attr.length == 1) {
        this.completeDateTo[this.completeDateTo.indexOf(attr)] = "0" + attr;
      }
    }

    this.model.fraTid = this.completeDateFrom[0] + "-" + this.completeDateFrom[1] + "-" + this.completeDateFrom[2] +
      "T" + this.completeDateFrom[3] + ":" + this.completeDateFrom[4] + ":00";
    // console.log(this.model.fraTid);

    this.model.tilTid = this.completeDateTo[0] + "-" + this.completeDateTo[1] + "-" + this.completeDateTo[2] +
      "T" + this.completeDateTo[3] + ":" + this.completeDateTo[4] + ":00";
    // console.log(this.model.tilTid);

    this.getVakter();
  }

  getVakter(): void {
    let response = this.fravaerService.getVaktByDate2(this.model.fraTid.substr(0, 10));
    Promise.resolve(response)
      .then(res => this.vaktliste = res)
      .then(/*() => console.log(this.vaktliste)*/);
  }

  updateUsers() : void {
    this.userService.getUsers1().subscribe(ret => this.users = this.userService.mapUsersFromObs(ret));
  }

  getBrukerVakter(): void {
    this.fravaerService.getBrukerVakts1().subscribe((obs) => this.brukervaktListe = this.fravaerService.mapBVFromObs(obs));
  }

  onSubmit(): void {
    if (this.selectedVakt == null || this.selectedUser == null) {
      return;
    }

    let tempListe: Fravaer[] = [];

    for (let bv of this.brukervaktListe) {
      tempListe[bv.vaktId] = bv;
    }
    let riktigVakt = tempListe[this.selectedVakt.vakt_id]

    // console.log(riktigVakt);

    if (riktigVakt == null) {
      alert("Valgt ansatt deltok ikke i denne vakten!");
      return;
    }

    this.model.brukerVaktId = riktigVakt.brukerVaktId;

    this.submitted = true;
    // console.log(this.model);
    this.fravaerService.registerFravaer(this.model);
    this.router.navigate(['../fravaer']);
  }

  ngOnInit() {
    this.updateUsers();
    this.getBrukerVakter();
  }
}
