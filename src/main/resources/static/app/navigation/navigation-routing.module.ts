import {Routes, RouterModule} from "@angular/router";
import {NavigationComponent} from "./navigation.component";
import {CalendarComponent} from "../calendar/calendar.component";
import {FravaerComponent} from "../fravaer/fravaer.component";
import {UserFormComponent} from "../user-form/user-form.component";
import {FaqComponent} from "../faq/faq.component";
import {ProfilComponent} from "../profil/profil.component";
import {NotificationComponent} from "../notification/notification.component";
import {NgModule} from "@angular/core";
import {UserinfoComponent} from "../userinfo/userinfo.component";
import {AuthGuard} from "../_guards/auth.guard";
import {FravaerInfoComponent} from "../fravaer-info/fravaer-info.component";
/**
 * Created by axelkvistad on 27/01/17.
 */
const navigationRoutes: Routes = [
  {
    path: '',
    redirectTo: '/navigation',
    pathMatch: 'full'
  },
  {
    path: 'navigation',
    component: NavigationComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: CalendarComponent
      },
      {
        path: 'fravaer',
        component: FravaerComponent
      },
      {
        path: 'fravaerinfo',
        component: FravaerInfoComponent
      },
      {
        path: 'opprett-bruker',
        component: UserFormComponent
      },
      {
        path: 'faq',
        component: FaqComponent
      },
      {
        path: 'profil',
        component: ProfilComponent
      },
      {
        path: 'meldinger',
        component: NotificationComponent
      },
      {
        path: 'brukerinfo',
        component: UserinfoComponent
      }

    ]

  }
];

@NgModule({
  imports: [
    RouterModule.forChild(navigationRoutes)
  ],
  exports: [
    RouterModule
  ]
})

export class NavigationRoutingModule {}
