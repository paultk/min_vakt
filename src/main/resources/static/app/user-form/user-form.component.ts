import {Component, OnInit} from '@angular/core';

import {User}    from '../_models/user';
import {UserService} from '../_services/user.service'
import {JsonTestClass} from "../_models/json-test-class";
import {USERS} from "../_mock/mock-ansatte";
import {AvdelingService} from "../_services/avdeling.service";
import {Avdeling} from "../_models/avdeling";

@Component({
  moduleId: module.id,
  selector: 'user-form',
  templateUrl: 'user-form.component.html',
  styleUrls: ['user-form.component.css']
})

export class UserFormComponent implements OnInit {
  avdelinger: Avdeling[];

  constructor(private userService: UserService,
              private avdelingService: AvdelingService) {
  }

  field2: string = 'field2';
  fieldValue2: string = 'fieldValue';

  stillingsProsent = [20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100];

  stillinger = ['Helsefagarbeider', 'Sykepleier', 'Assistent'];

  /*
   avdeling = ['St. Olavs Hospital', 'Sentrum sykehus', 'Lade hjelpehjem', 'Ranheim eldreboliger'];
   */

  model = USERS[1];

  submitted = false;

  jsonTest = new JsonTestClass('dsffsd');

  /*testConnect(): void {
    // console.log('ping2');
    this.userService.testConnect2();
  }*/

  testConnect4(): void {
    console.log(this.model.fornavn)
  }

  onSubmit() {
    this.submitted = true;
    console.log(this.model);
    this.userService.addUser(this.model);
  }

  printFunksjon(): void {
    console.log(this.model);
  }

  getAvdelinger(): void {
    let response = this.avdelingService.getAvdelinger();
    Promise.resolve(response).then(avdelinger => this.avdelinger = avdelinger).then(() => console.log(this.avdelinger));
  }

  /*Backup
   getAvdelinger(): void {
   let response = this.avdelingService.getAvdelinger();
   Promise.resolve(response).then(res => console.log(res));
   }
   */

  ngOnInit(): void {
    this.getAvdelinger();
  }
}
