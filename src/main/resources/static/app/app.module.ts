import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RouterModule, Routes} from '@angular/router';

import {AppComponent}  from './app.component';
import {NavigationComponent} from './navigation/navigation.component'
import {ProfilComponent} from './profil/profil.component';
import {UserinfoComponent} from './userinfo/userinfo.component';
import {LoginComponent} from './login/login.component';
import {UserFormComponent} from './user-form/user-form.component';
import {UserService} from './_services/user.service';
import {FravaerService} from './_services/fravaer.service';
import {AvdelingService} from './_services/avdeling.service'
import {InputFieldComponent} from './input-field/input-field.component';
import {ForgotCredentialsComponent} from './forgot-credentials/forgot-credentials.component';
import {FravaerComponent} from './fravaer/fravaer.component';
import {FaqComponent} from "./faq/faq.component";
import {NavBarComponent} from "./calendar/nav-bar.component";
import {ShiftService} from "./_services/shift.service";
// import "./rxjs-extensions";
import {NotificationComponent} from "./notification/notification.component";
import {NotificationService} from "./_services/notification.service";
import {AuthenticationService} from './_services/authentication.service';
import {UserSearchcomponent} from './userinfo/user-search.component'

import {AppRoutingModule} from './app-routing.module';
import {AuthGuard} from "./_guards/auth.guard";
import {PageNotFoundComponent} from "./not-found.component";
import {CommonModule} from "@angular/common";
import {NavigationModule} from "./navigation/navigation.module";
import {VaktBytteComponent} from "./vakt_bytte/vakt-bytte.component";

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    NavigationModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    NavigationComponent,
    LoginComponent,
    PageNotFoundComponent
  ],
  providers: [
    AuthenticationService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}

