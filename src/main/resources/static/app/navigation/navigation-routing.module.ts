import {Routes, RouterModule} from "@angular/router";
import {NavigationComponent} from "./navigation.component";
import {AdminCalendarComponent} from "../calendar/calendar_for_admin/admin-calendar.component";
import {UsersCalendarComponent} from "../calendar/calendar_for_user/users-calendar.component";
import {FravaerComponent} from "../fravaer/fravaer.component";
import {UserFormComponent} from "../user-form/user-form.component";
import {FaqComponent} from "../faq/faq.component";
import {ProfilComponent} from "../profil/profil.component";
import {NotificationComponent} from "../notification/notification.component";
import {NgModule} from "@angular/core";
import {UserinfoComponent} from "../userinfo/userinfo.component";
import {AuthGuard} from "../_guards/auth.guard";
import {FravaerInfoComponent} from "../fravaer-info/fravaer-info.component";
import {VaktBytteComponent} from "../vakt_bytte/vakt-bytte.component";
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
        component: UsersCalendarComponent
      },
      {
        path: 'admin',
        component: AdminCalendarComponent
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
        path: 'rediger-bruker',
        component: ProfilComponent
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
      },
      {
        path: 'vaktbytte',
        component: VaktBytteComponent
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
