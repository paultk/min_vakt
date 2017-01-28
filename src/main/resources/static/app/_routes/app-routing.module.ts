/**
 * Created by axelkvistad on 27/01/17.
 */
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from '../login/login.component';
import {AuthGuard} from "../_guards/auth.guard";
import {PageNotFoundComponent} from "../not-found.component";

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/navigation',
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];
@NgModule({
  imports: [ RouterModule.forRoot(appRoutes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
