///<reference path="../_services/fravaer.service.ts"/>
/**
 * Created by axelkvistad on 27/01/17.
 */

import {NgModule} from '@angular/core'
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from "@angular/http";
import {BrowserModule} from "@angular/platform-browser";
import {CalendarComponent} from "../calendar/calendar.component";
import {FravaerComponent} from "../fravaer/fravaer.component";
import {UserFormComponent} from "../user-form/user-form.component";
import {FaqComponent} from "../faq/faq.component";
import {ProfilComponent} from "../profil/profil.component";
import {NotificationComponent} from "../notification/notification.component";
import {UserinfoComponent} from "../userinfo/userinfo.component";
import {LoginComponent} from "../login/login.component";
import {InputFieldComponent} from "../input-field/input-field.component";
import {ForgotCredentialsComponent} from "../forgot-credentials/forgot-credentials.component";
import {NavBarComponent} from "../calendar/nav-bar.component";
import {UserService} from "../_services/user.service";
import {FravaerService} from "../_services/fravaer.service";
import {ShiftService} from "../_services/shift.service";
import {AvdelingService} from "../_services/avdeling.service";
import {NotificationService} from "../_services/notification.service";
import {AuthenticationService} from "../_services/authentication.service";
import {NavigationRoutingModule} from "./navigation-routing.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {UserSearchcomponent} from "../userinfo/user-search.component";
import {FravaerInfoComponent} from "../fravaer-info/fravaer-info.component";

@NgModule({
  imports: [
    NgbModule.forRoot(),
    CommonModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    NavigationRoutingModule
  ],
  declarations: [
    UserinfoComponent,
    ProfilComponent,
    UserFormComponent,
    InputFieldComponent,
    ForgotCredentialsComponent,
    FravaerComponent,
    FravaerInfoComponent,
    FaqComponent,
    CalendarComponent,
    NavBarComponent,
    FaqComponent,
    // LoginComponent,
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
    AuthenticationService,
  ]
})

export class NavigationModule{}
