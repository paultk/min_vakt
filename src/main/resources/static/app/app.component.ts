import {Component} from '@angular/core';

@Component({
  selector: 'my-app',
  template: `
    <my-navigation></my-navigation>
    <!--<calendar-component></calendar-component>-->
    <!--<router-outlet></router-outlet>
    <!--<my-login></my-login>-->
  `,
})

export class AppComponent {
  name = 'Angular';
}

