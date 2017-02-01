import {Component} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { User } from '../_models/user';
import {Authentication} from "../_models/authentication";
import {Http, Headers} from "@angular/http";
import {AuthenticationService} from "../_services/authentication.service";

@Component({
  moduleId: module.id,
  selector: 'my-login',
  templateUrl: 'login.component.html',
  styleUrls: [ 'login.component.css' ]
})

export class LoginComponent {
  model = new Authentication("admin", "Admin@@@");
  rememberMe = false;
  loading = false;
  returnUrl: string;

  theUser: User;

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ){}

  private headers = new Headers({'Content-Type': 'application/json'});

  login() {
    this.authService.logout();
    this.loading = true;
    this.authService.login(this.model)
      .subscribe(
        data => {
          this.authService.setCurrentUser(localStorage.getItem('currentUserEmail'))
            .subscribe((observable) => this.restOfSetUser(observable));
        },
        error => {
          this.loading = false;
          console.log("failure: " + error);
          alert("Feil brukernavn/passord! Prøv igjen");
        });
  }

  goToNavigation() {
    if (this.theUser.admin) {
      this.router.navigate(['/navigation/admin']);
    } else {
      this.router.navigate(['/navigation']);
    }
  }

  restOfSetUser(user: User[]): void {

    this.theUser = new User(user['brukerId'], user['passordId'], user['stillingsBeskrivelse'], user['telefonNr'], user['stillingsProsent'],
      user['timelonn'], user['admin'], user['fornavn'], user['etternavn'], user['epost'], user['avdelingId'], user['plaintextPassord'],
      user['fodselsdato'], user['adresse'], user['by'], user['hash'], user['salt']);

    if (this.rememberMe) {
      localStorage.setItem('currentUser', JSON.stringify(this.theUser));
    } else {
      sessionStorage.setItem('currentUser', JSON.stringify(this.theUser));
    }

    localStorage.setItem('rememberMe', this.rememberMe.toString());
    //console.log(localStorage.getItem('rememberMe'));


    let globalDeadMan = this.authService.getGlobalUser();
    console.log(globalDeadMan);

    this.goToNavigation();
  }

  onSubmit(): void {
    this.login();
  }
}
