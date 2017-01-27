import {Component} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { User } from '../_models/user';
import {Authentication} from "../_models/authentication";
import {Http, Headers} from "@angular/http";
import {AuthenticationService} from "../_services/authentication.service";
import 'rxjs/add/operator/map';

@Component({
  moduleId: module.id,
  selector: 'my-login',
  templateUrl: 'login.component.html',
  styleUrls: [ 'login.component.css' ]
})

export class LoginComponent {
  model = new Authentication("root@minvakt.no", "abcDEF!#");
  rememberMe = false;
  loading = false;
  returnUrl: string;

  theUser: User[] = [];

  constructor(
    private http: Http,
    private authService: AuthenticationService,
    private router: Router
  ){}

  private headers = new Headers({'Content-Type': 'application/json'});

  login() {
    this.loading = true;
    this.authService.login(this.model)
      .subscribe(
        data => {
          this.authService.setCurrentUser(localStorage.getItem('currentUserEmail'))
            .subscribe((observable) => this.restOfSetUser(observable));
          this.goToNavigation();
        },
        error => {
          this.loading = false;
          console.log("failure: " + error);
          alert("Feil brukernavn/passord! Prøv igjen");
        });
  }

  goToNavigation() {
    this.router.navigate(['/navigation']);
  }

  restOfSetUser(users: User[]): void {

    this.theUser = users.map(user => new User(user['brukerId'], user['passordId'], user['stillingsBeskrivelse'], user['telefonNr'], user['stillingsProsent'],
      user['timelonn'], user['admin'], user['fornavn'], user['etternavn'], user['epost'], user['avdelingId'], user['plaintextPassord'],
      user['fodselsdato'], user['adresse'], user['by'], user['hash'], user['salt']));

    localStorage.setItem('currentUser', JSON.stringify(this.theUser[0]));

    let globalDeadMan = this.authService.getGlobalUser();
    console.log(globalDeadMan);
  }

  onSubmit(): void {
    this.login();
  }
}
