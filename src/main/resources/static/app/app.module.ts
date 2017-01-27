import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent}  from './app.component';
import {NavigationComponent} from './navigation.component'
import {ProfilComponent} from './profil.component';
import {UserinfoComponent} from './userinfo.component';
import {LoginComponent} from './login.component';
import {UserFormComponent} from './user-form.component';
import {UserService} from './user.service';
import {FravaerService} from './fravaer.service';
import {AvdelingService} from './avdeling.service'
import {InputFieldComponent} from './input-field.component';
import {ForgotCredentialsComponent} from './forgot-credentials.component';
import {FravaerComponent} from './fravaer.component';
import {FaqComponent} from "./faq.component";
import {CalendarComponent} from "./calendar.component";
import {NavBarComponent} from "./nav-bar.component";
import {ShiftService} from "./shift.service";
// import "./rxjs-extensions";
import {NotificationComponent} from "./notification.component";
import {NotificationService} from "./notification.service";
import {AuthenticationService} from './authentication.service';
import {UserSearchcomponent} from './user-search.component'

import {AppRoutingModule} from './app-routing.module';

@NgModule({
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    NavigationComponent,
    LoginComponent,
    UserinfoComponent,
    ProfilComponent,
    UserFormComponent,
    InputFieldComponent,
    ForgotCredentialsComponent,
    FravaerComponent,
    FaqComponent,
    CalendarComponent,
    NavBarComponent,
    FaqComponent,
    NotificationComponent,
    UserSearchcomponent
  ],
  providers: [
    UserService,
    FravaerService,
    ShiftService,
    FravaerService,
    AvdelingService,
    NotificationService,
    AuthenticationService
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}

