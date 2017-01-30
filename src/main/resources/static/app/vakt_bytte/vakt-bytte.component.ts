import {Component, OnInit} from '@angular/core';

import { Fravaer } from '../_models/fravaer';
import {Vakt} from '../_models/vakt';
import {FravaerService} from "../_services/fravaer.service";
import {User} from "../_models/user";
import {Shift} from "../_models/shift";
import { UserService } from "../_services/user.service";
import { ShiftService } from "../_services/shift.service";

@Component({
  moduleId: module.id,
  selector: 'vakt-bytte',
  templateUrl: 'vakt-bytte.component.html',
  styleUrls: [ 'vakt-bytte.component.css' ]
})

export class VaktBytteComponent implements OnInit{
  constructor(
    private userService: UserService,
    private shiftService: ShiftService,
  ) {}

  brukerId2 = 0;

  months = ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'];
  date: Date;
  allUsers: User[];
  usersIndexed: User[] = [];
  monthShifts: any[] = [];
  daysShifts: Shift[][] = [];
  vaktBytter: any[];
  shiftsIndexedById: Shift[] = [];


  ngOnInit(): void {
    this.date = new Date();

    this.userService.getUsers1()
      .subscribe(
        (observable) => this.restOfInit(observable)
      );


  }

  getVaktBytter(): void {
    this.shiftService.getVaktBytter().subscribe(
      response => this.vaktBytter = response
    )
  }

  getShifts(): void {
    this.shiftService.getShifts(this.date).subscribe(
      (val) => this.setShifts(val)
    );

  }

  restOfInit(users: User[]): void {
    this.allUsers = users.map(user =>  new User(user['brukerId'], null, user['stillingsBeskrivelse'], null, user['stillingsProsent'],
      null, null, user['fornavn'], user['etternavn'], user['epost'], user['avdelingId']
    ));

    this.getShifts();
  }

  //todo: minimalize shift
  setShifts(shifts: any[]): void {

    // console.log('shifts');
    // console.log(shifts);

    this.monthShifts = shifts;

    for (let i = 0; i < 32; i++) {
      this.daysShifts[i] = [];
    }
    for (let i in this.monthShifts) {
      let date = new Date(this.monthShifts[i]['vakt']['tilTid']);
      let vaktAnsvarligId = this.monthShifts[i]['vakt']['vaktansvarligId'];
      // night = toTime = 0700, day = toTime = 1500, evening = toTime = 2300
      let toTimeSliced = this.monthShifts[i]['vakt']['tilTid'].slice(11, 13) as number;
      let fromTime = this.monthShifts[i]['vakt']['tilTid'].slice(0, 16) as number;
      let shiftDescript = 7;
      if (toTimeSliced > 7 && toTimeSliced < 1500) shiftDescript = 15;
      else if (toTimeSliced > 1500) shiftDescript = 23;

      //todo: extremely hacky, should be fixed


      let brukerId = (this.monthShifts[i]['brukerId'] == 0) ? vaktAnsvarligId : this.monthShifts[i]['brukerId'];
      let vaktId = this.monthShifts[i]['vakt']['vaktId'];

      let tempShift = new Shift(null, fromTime, shiftDescript, brukerId, vaktAnsvarligId,  vaktId);

      this.shiftsIndexedById[tempShift.vaktId] = tempShift;
      this.daysShifts[date.getDate()].push(
        tempShift);


    }



    let daysInMonth = new Date(this.date.getFullYear(), this.date.getMonth(), 0).getDate();

    this.allUsers.forEach(
      user =>
        this.usersIndexed[user.brukerId] = user
    );
    this.monthShifts.forEach(
      shift => this.shiftsIndexedById[shift.vaktId] = shift
    );

    //this.vaktInForm = this.allUsers[0];

    this.getVaktBytter();

    this.brukerId2 = this.allUsers[0].brukerId;

  }


  godkjennVaktBytte(vaktBytte: any): void {
    if(vaktBytte['brukerId2'] == 0) vaktBytte['brukerId2'] = this.brukerId2;
    this.shiftService.byttVakter(vaktBytte);

  }

  slettVaktBytte(vaktBytte: any): void {
    this.shiftService.deleteVaktBytte(vaktBytte);

    for(let i in this.vaktBytter) {
      if(this.vaktBytter[i] == vaktBytte) {
        this.vaktBytter.splice( Number(i) , 1);
        break;
      }
    }
  }

  check(): void {
  }

  changeDate(year: number, month: number, date: number): void {
    let prevMonth = this.date.getMonth();
    let prevYear = this.date.getFullYear();
    if(this.date.getDate() != date) {
      this.date.setDate(date)
    }
    else if (this.date.getMonth() != month) {
      if(Number(this.shiftService.daysInMonth(this.date)) > Number(this.shiftService.daysInMonth(new Date(year, month, date)))) {
        this.date.setDate(Number(this.shiftService.daysInMonth(new Date(year, month, date))));
        this.date.setMonth(month);
      }
    }
    else if (this.date.getFullYear() != year) {
      this.date.setFullYear(year);
    }
    if (this.date.getFullYear() != prevYear || this.date.getMonth() != prevMonth){
      this.getShifts();
    }
  }
}
