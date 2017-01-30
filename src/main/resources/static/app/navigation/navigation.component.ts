import {Component, OnInit} from "@angular/core";

import {User} from '../_models/user';
import {UserService} from '../_services/user.service';
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "../_services/authentication.service";

@Component({
  moduleId: module.id,
  selector: 'my-navigation',
  templateUrl: 'navigation.component.html',
  styleUrls: ['navigation.component.css']
})

export class NavigationComponent implements OnInit{
  users: User[];
  numMessages = 0;


  private headers = new Headers({'Content-Type': 'application/json', 'token': localStorage.getItem('sessionToken')});


  constructor (
    private userService: UserService,
    private http: Http,
    private router: Router,
    private authService: AuthenticationService
) {}

  getUsers(): void {
     this.userService.getUsers().then(users => this.users = users);
  }

  /*goToCalendar() {
    this.router.navigate(['/calendar']);
  }*/

  setNumMessages(): void {
   // this.selectedUser = this.authService.getGlobalUser();

    const URL = 'http://localhost:8080/melding/get/ulest/ant';
    console.log(this.authService.getGlobalUser().brukerId);
    this.http.post(URL, JSON.stringify(this.authService.getGlobalUser()), {headers: this.headers},)
      .toPromise()
      .then((res) => {
        this.numMessages = parseInt(res.text());
    })
      .catch((res) => {})
  }
  setEdit() : void {
    // console.log("hallo");
    this.userService.setEditUser(this.authService.getGlobalUser());
  }
  hide() : boolean {
    return this.numMessages != 0;
  }

  ngOnInit(): void {
    this.getUsers();
   this.setNumMessages();
   setInterval(() => {this.setNumMessages();}, 2000);
  }

  logout(): void {
    this.authService.logout();
  }
}
