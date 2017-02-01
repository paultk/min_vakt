/**
 * Created by axelkvistad on 27/01/17.
 */
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import {NavigationComponent} from "./navigation/navigation.component";
import {AuthGuard} from "./_guards/auth.guard";
import {PageNotFoundComponent} from "./not-found.component";
import {ForgotCredentialsComponent} from "./forgot-credentials/forgot-credentials.component";

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/navigation',
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    children: [
      {
        path: 'glemt-passord',
        component: ForgotCredentialsComponent,
        outlet: 'popup'
      }
    ]
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];
@NgModule({
  imports: [ RouterModule.forRoot(appRoutes, { useHash: true }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
