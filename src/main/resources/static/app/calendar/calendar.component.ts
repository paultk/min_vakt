import {Component, Input, OnInit} from "@angular/core";
import {User} from "../_models/user";
import {Shift} from "../_models/shift";
import {ShiftService} from "../_services/shift.service";
import {UserService} from "../_services/user.service";
import {isUndefined} from "util";
import {isNullOrUndefined} from "util";

//todo: possibly fix the way percentage of workers handles shift display
//todo: Alphabetize users displayed
//todo: change shifts




@Component({
  moduleId: module.id,
  selector: 'calendar-component',
  templateUrl: 'calendar.component.html',
  styleUrls: [ 'calendar.component.css']
})

export class CalendarComponent implements OnInit {
  constructor(
    private shiftService: ShiftService,
    private userService: UserService
  ) {
  }

  cssClasses: string[] =[];



  // array where index = userId
  usersIndexed: User[] = [];

  // todo: fix avdelsId to match administrator
  static avdelingsId = 2;

  monthShifts: any[] = [];

  daysShifts: Shift[][] = [];

  //  list of shifts that are in the system
  //  list of users that can work that day, with from and to time
  shiftsUsersCanWork: Shift[];
  shiftInForm: Shift;
  date: Date;
  allUsers: User[] = [];
  tempArr = new Array(24);
  months = ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'];

  shifts: Shift[] = [
    new Shift(null, null, null, 1),
    new Shift(null, null, null, 49),

  ];

  restOfInit(users: User[]): void {


    this.allUsers = users.map(user => new User(user['brukerId'], null, user['stillingsBeskrivelse'], null, user['stillingsProsent'],
      null, null, user['fornavn'], user['etternavn'], user['epost'], user['avdelingId']


    ));
    console.log(this.allUsers);
  }


  ngOnInit(): void {

    this.cssClasses['Helsefagarbeider'] = 'yellowDiv';
    this.cssClasses['Assistent'] = 'greenDiv';
    this.cssClasses['Sykepleier'] = 'redDiv';


    let time1 = new Date();
    console.log('zero' + time1);


    this.date = new Date();

    this.shiftInForm = new Shift(new User(null, null, null, null, null, null, null, 'Fornavn', 'Etternavn'), 0, 0)


    this.userService.getUsers1()
      .subscribe(
        (observable) => this.restOfInit(observable)
      )    ;
    // this.restOfInit();
    // this.takenShift = this.shiftService.getShifts(new Date(1221));
    this.shiftsUsersCanWork = this.shiftService.getShiftsUsersCanWork(null);
    this.getShifts();

    let time = new Date();
    console.log('third' + time);

    this.setPercentageList();
    this.checkIfPercentageIsOk();


    time = new Date();
    console.log('fourth' + time);

  }

  setForm(user: User): void {
    this.shiftInForm.user = user;
  }

  setPercentageList(): void {

    let stillingsIdStrings: string[] = [];
    stillingsIdStrings['Helsefagarbeider'] = 0;
    stillingsIdStrings['Assistent'] = 1;
    stillingsIdStrings['Sykepleier'] = 2;


    let currDate = this.date.getDate();
    for (let i in this.daysShifts[currDate]) {

      // 0: 2300-0700, 1: 0700-1500, 2: 1500-2300
      // todo: fix shift time handling, changes on db would make it easier
      //   todo: needs fix
      let shiftPeriod = this.daysShifts[currDate][i].toTime % 7;
      let stilling = stillingsIdStrings[this.usersIndexed[this.daysShifts[currDate][i].userId].stillingsBeskrivelse];
      this.percentageList[shiftPeriod][stilling] += 1;

    }
  }

  checkIfPercentageIsOk(): void {
    for(let i = 0; i < 3; i++) {
      let total = 0;
      for (let j = 0; j < 3; j++) {
        total += this.percentageList[i][j];
      }
      if (total <= 0) return;
      else {
        this.shiftPercentageOk[i] = ((this.percentageList[i][0] / total) >= 0.3 && (this.percentageList[i][0] / total) >= 0.2)
      }
    }
  }

  ordinaryTimeAndOvertime = {};
  // 0 = HelsefagArbeider(30 %), 1 = Sykepleier(20%), 2 = Assistent
  // Three shifts a day
  percentageList = [[0,0,0], [0,0,0], [0,0,0]];
  shiftPercentageOk = [false, false, false];

  //todo: possibly limit getUsers to avdeling
  // sets all the users from the db

  //val[0]['vakt']['tilTid']

  getShifts(): void {
    this.shiftService.getShifts(this.date).subscribe(
      (val) => this.setShifts(val)
    );


  }

  //todo: minimalize shift
  setShifts(shifts: any[]): void {

    let time = new Date();
    console.log('second' + time);

    console.log('shifts:');
    console.log(shifts);
    this.monthShifts = shifts;
    for (let i = 0; i < 32; i++) {
      this.daysShifts[i] = [];
    }

    for (let i in this.monthShifts) {
      let date = new Date(this.monthShifts[i]['vakt']['tilTid']);
      // night = toTime = 0700, day = toTime = 1500, evening = toTime = 2300
      let shiftDivided: number[] = [];
      let toTimeSliced = this.monthShifts[i]['vakt']['tilTid'].slice(11, 13) as number;
      let shiftDescript = 7;
      if (toTimeSliced > 7 && toTimeSliced < 1500) shiftDescript = 15;
      else if (toTimeSliced > 1500) shiftDescript = 23;

      this.daysShifts[date.getDate()].push(
        new Shift(null, 0, shiftDescript, this.monthShifts[i]['brukerId'] ));
    }


    let daysInMonth = new Date(this.date.getFullYear(), this.date.getMonth(), 0).getDate();

    this.allUsers.forEach(
      user =>
        this.usersIndexed[user.brukerId] = user
    );

    this.allUsers.forEach( user =>
      this.ordinaryTimeAndOvertime[user.brukerId] = [Math.round( (37.5 * daysInMonth / 7 ) * (user.stillingsProsent / 100)), 0]);
    // user.stillingsProsent / 100 * daysInMonth * 8), 0, user.stillingsProsent]);

    // todo: possibly fix the workaround for amount of hours a month


    this.daysShifts.forEach(
      shiftarr =>
        shiftarr.forEach( (shift) => this.ordinaryTimeAndOvertime[shift.userId][0] += 8));


    // loop to display ordinary time, or overtime
    for (let i in this.ordinaryTimeAndOvertime) {
      if(this.ordinaryTimeAndOvertime[i][0] < 0) {
        this.ordinaryTimeAndOvertime[i][1] = this.ordinaryTimeAndOvertime[i][0] * -1;
        this.ordinaryTimeAndOvertime[i][0] = 0;
      }
    }
  }
  registerShift(): void {
    console.log(this.daysShifts);

  }



  changeDate(year: number, month: number, date: number): void {

    let prevMonth = this.date.getMonth();
    let prevYear = this.date.getFullYear();

    if(this.date.getDate() != date) {
      this.date.setDate(date)
    }
    else if (this.date.getMonth() != month) {
      this.date.setMonth(month);
    }
    else if (this.date.getFullYear() != year) {
      this.date.setFullYear(year);
    }
    if (this.date.getFullYear() != prevYear || this.date.getMonth() != prevMonth){
      this.getShifts();


    }
    this.setPercentageList();
    this.checkIfPercentageIsOk();

  }
}
