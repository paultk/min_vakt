import {Component, HostBinding} from '@angular/core';
import {AuthenticationService} from "../_services/authentication.service";
import {Email} from "../_models/email";
import {UserService} from "../_services/user.service";
import {User} from "../_models/user";
import { Router } from '@angular/router';
import {slideInDownAnimation} from '../animations';
@Component({
  moduleId: module.id,
  selector: 'my-forgot-credentials',
  templateUrl: 'forgot-credentials.component.html',
  styleUrls: [ 'forgot-credentials.component.css' ],
  styles: [ ':host { position: relative; bottom: 10%; }' ],
})

export class ForgotCredentialsComponent {
  @HostBinding('style.display')   display = 'block';
  @HostBinding('style.position')  position = 'absolute';

  model = new Email('');
  user = new User();

  constructor (
    private userService: UserService,
    private router: Router
  ){}

  forgotPassword(): void {
    this.user.epost = this.model.recipient;
    this.userService.resetUserPassword(this.user);
  }

  cancel() {
      console.log('cancel');
      this.closePopup();
  }

  closePopup(): void {
      console.log('close');
    this.router.navigate([{ outlets: { popup: null }}]);
  }
}
