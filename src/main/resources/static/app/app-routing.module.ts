import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import {NavigationComponent} from "./navigation/navigation.component";
import {CalendarComponent} from "./calendar/calendar.component";
import {AuthGuard} from "./_guards/auth.guard";

const routes: Routes = [
  { path: '', component: NavigationComponent, canActivate: [AuthGuard] },
  { path: 'login',  component: LoginComponent },
  { path: 'navigation', component: NavigationComponent },
  { path: 'calendar', component: CalendarComponent },
  { path: '**', redirectTo: ''}
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
