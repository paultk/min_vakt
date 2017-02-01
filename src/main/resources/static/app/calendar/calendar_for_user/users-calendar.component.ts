import {Component, Input, OnInit} from "@angular/core";
import {ShiftService} from "../../_services/shift.service";
import {UserService} from "../../_services/user.service";
import {User} from "../../_models/user";
import {Shift} from "../../_models/shift";
import {AuthenticationService} from "../../_services/authentication.service";


//todo: possibly fix the way percentage of workers handles shift display
//todo: Alphabetize users displayed
//todo: change shifts




@Component({
  moduleId: module.id,
  selector: 'users-calendar-component',
  templateUrl: 'users-calendar.component.html',
  styleUrls: [ 'users-calendar.component.css']
})

export class UsersCalendarComponent implements OnInit {
  constructor(
    private shiftService: ShiftService,
    private authenticationService: AuthenticationService,
    private userService: UserService
  ) {
  }

  userHaveShiftThisDay = false;

  availableHour1= '23:00:00';
  availableHour2= '07:00:00';

  availables: any[] = [];

  nullUser: User = new User(null, null, null, null, null, null, null, '', '');

  vaktForBytte1 = new Shift(this.nullUser, 0, 0);

  byttVakt = false;
  cssClasses: string[] =[];

  vaktansvarligIds: number[][]= [];

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
  tempArr = new Array(23);
  months = ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'];

  ordinaryTimeAndOvertime = {};
  // 0 = HelsefagArbeider(30 %), 1 = Sykepleier(20%), 2 = Assistent
  // Three shifts a day
  percentageList = [[0,0,0], [0,0,0], [0,0,0]];
  shiftPercentageOk = [false, false, false];


  availabilityClicked = false;
  availabilityOk = false;

  restOfInit(users: User[]): void {
    this.allUsers = users.map(user =>  new User(user['brukerId'], null, user['stillingsBeskrivelse'], null, user['stillingsProsent'],
      null, null, user['fornavn'], user['etternavn'], user['epost'], user['avdelingId']
    ));
    this.allUsers.forEach(
      user =>
        this.usersIndexed[user.brukerId] = user
    );
  }


  ngOnInit(): void {
    for (let i = 0; i < 500; i++) {
      this.ordinaryTimeAndOvertime[i] = new Array();
      this.ordinaryTimeAndOvertime[i][0] = 0;
    }

    this.cssClasses['Helsefagarbeider'] = 'yellow-div-table';
    this.cssClasses['Assistent'] = 'green-div-table';
    this.cssClasses['Sykepleier'] = 'red-div-table';



    this.date = new Date();

    this.shiftInForm = new Shift(new User(null, null, null, null, null, null, null, 'Fornavn', 'Etternavn'), 0, 0);


    this.userService.getUsers1()
      .subscribe(
        (observable) => this.restOfInit(observable)
      );

    this.getShifts();


    this.setPercentageList();
    this.checkIfPercentageIsOk();




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

  getShifts(): void {
    this.shiftService.getShifts(this.date).subscribe(
      (val) => this.setShifts(val)
    );


  }

  //todo: minimalize shift
  setShifts(shifts: any[]): void {

    this.monthShifts = shifts;
    for (let i = 0; i < 32; i++) {
      this.daysShifts[i] = [];
    }
    for (let i in this.monthShifts) {
      let date = new Date(this.monthShifts[i]['vakt']['tilTid']);
      let vaktAnsvarligId = this.monthShifts[i]['vakt']['vaktansvarligId'];
      // night = toTime = 0700, day = toTime = 1500, evening = toTime = 2300
      let toTimeSliced = this.monthShifts[i]['vakt']['tilTid'].slice(11, 13) as number;
      let shiftDescript = 7;
      if (toTimeSliced > 7 && toTimeSliced < 1500) shiftDescript = 15;
      else if (toTimeSliced > 1500) shiftDescript = 23;

      //todo: extremely hacky, should be fixed

      this.vaktansvarligIds[date.getDate()] = [];
      this.vaktansvarligIds[date.getDate()][shiftDescript] = vaktAnsvarligId;

      let brukerId = (this.monthShifts[i]['brukerId'] == 0) ? vaktAnsvarligId : this.monthShifts[i]['brukerId'];

      this.daysShifts[date.getDate()].push(
        new Shift(null, 0, shiftDescript, brukerId, vaktAnsvarligId ));
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
        shiftarr.forEach( (shift) => this.ordinaryTimeAndOvertime[shift.userId][0] -= 8));


    // loop to display ordinary time, or overtime
    for (let i in this.ordinaryTimeAndOvertime) {
      if(this.ordinaryTimeAndOvertime[i][0] < 0) {
        this.ordinaryTimeAndOvertime[i][1] = this.ordinaryTimeAndOvertime[i][0] * -1;
        this.ordinaryTimeAndOvertime[i][0] = 0;
      }
    }

    //  sets availables
    this.shiftService.getAvailables(this.date).subscribe(res => this.setAvailables(res));

    if(this.userHaveShift() != null) {
      this.vaktForBytte1.user = this.userHaveShift();
    }

  }
  registerShift(): void {
    console.log(this.shiftInForm);
    let dateStringed = this.date.toISOString().substr(0, 10)+ 'T';
    console.log(dateStringed);
    this.shiftService.addShift(this.shiftInForm, dateStringed);

  }

  changeDate(year: number, month: number, date: number): void {
    console.log(this.userHaveShiftThisDay);
    console.log(this.getCurrentUser().fornavn);
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
    this.setPercentageList();
    this.checkIfPercentageIsOk();
  }

  userHaveShift(): Shift {
    this.userHaveShiftThisDay = false;
    for (let shift of this.daysShifts[this.date.getDate()]) {
      if(shift.userId == this.getCurrentUser().brukerId){
        this.userHaveShiftThisDay = true;
        return shift;
      }
    }
    return null;
  }
  setshiftInChangeShift(shift: Shift) {
    if(this.byttVakt) {
      this.vaktForBytte1.user = this.usersIndexed[shift.userId];
      // this.vaktForBytte1.fromTime = shift.userId;
    }
  }

  switchShifts(): void {
    let vaktBytte = {'brukerId1': this.getCurrentUser().brukerId, 'vaktId': this.userHaveShift(), 'brukerId2': this.vaktForBytte1};
    this.shiftService.addVaktBytte(vaktBytte);


  }

  setAvailables(availablesObs:any) {
    this.availables = [];

    for (let available of availablesObs) {
      if (this.availables[available['tilTid'].substr(0, 10)] == undefined) {
        this.availables[available['tilTid'].substr(0, 10)] = [];
      }

      this.availables[available['tilTid'].substr(0, 10)].push([this.usersIndexed[available['userId']], available['fraTid'], available['tilTid']]);
    }
  }

  registerAvailability(val: any):void {
    let date1 = this.date.toISOString().substr(0, 11);
    let date2 = this.date.toISOString().substr(0, 11);

    let returnObj = {'fraTid': (date1 + this.availableHour1), 'tilTid': (date2 + this.availableHour2), 'userId': this.getCurrentUser().brukerId};
    this.shiftService.addAvailability(returnObj).then(res => this.availabilityOk = res);

    this.availabilityClicked = true;

  }

  check(): void {
    console.log(this.availables);
  }

  //todo: needs to reference actual user
  getCurrentUser(): User{
    return this.authenticationService.getGlobalUser();
  }
}
