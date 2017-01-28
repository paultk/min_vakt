import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from '../login/login.component';
import {NavigationComponent} from "../navigation/navigation.component";
import {AuthGuard} from "../_guards/auth.guard";
import {CalendarComponent} from "../calendar/calendar.component";
import {FaqComponent} from "../faq/faq.component";
import {FravaerComponent} from "../fravaer/fravaer.component";
import {UserFormComponent} from "../user-form/user-form.component";
import {ProfilComponent} from "../profil/profil.component";
import {UserinfoComponent} from "../userinfo/userinfo.component";
import {NotificationComponent} from "../notification/notification.component";

const routes: Routes = [
  { path: '', component: NavigationComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'navigation', component: NavigationComponent},
  { path: '**', redirectTo: ''},
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
