
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import {NavigationComponent} from "./navigation/navigation.component";
import {AuthGuard} from "./_guards/auth.guard";

const routes: Routes = [
  { path: '', component: NavigationComponent, canActivate: [AuthGuard] },
  { path: 'login',  component: LoginComponent },
  { path: 'navigation', component: NavigationComponent },
  { path: '**', redirectTo: ''}
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}



/*
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import {NavigationComponent} from "./navigation/navigation.component";
import {AuthGuard} from "./_guards/auth.guard";
import {CalendarComponent} from "./calendar/calendar.component";
import {FaqComponent} from "./faq/faq.component";
import {FravaerComponent} from "./fravaer/fravaer.component";
import {UserFormComponent} from "./user-form/user-form.component";
import {ProfilComponent} from "./profil/profil.component";
import {UserinfoComponent} from "./userinfo/userinfo.component";
import {NotificationComponent} from "./notification/notification.component";

const routes: Routes = [
  { path: '**', redirectTo: ''},
  { path: '', component: NavigationComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'navigation', component: NavigationComponent/!*, children: [
    { path: '', redirectTo: '/calendar', pathMatch: 'full' },
    { path: 'calendar',  component: CalendarComponent },
    { path: 'faq', component: FaqComponent },
    { path: 'fravaer', component: FravaerComponent },
    { path: 'user-form', component: UserFormComponent },
    { path: 'profil', component: ProfilComponent },
    { path: 'userinfo', component: UserinfoComponent },
    { path: 'notification', component: NotificationComponent },
  ]*!/}
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}


//export const routing = RouterModule.forRoot(routes);
*/
