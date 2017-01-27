/**
 * Created by Trym Todalshaug on 12/01/2017.
 */

import {Component, OnInit} from "@angular/core";

import {User} from '../_models/user';
import {UserService} from '../_services/user.service';
/*var $ = require("jquery");*/

@Component({
  moduleId: module.id,
  selector: 'my-userinfo',
  templateUrl: 'userinfo.component.html',
  styleUrls: ['userinfo.component.css']
})

export class UserinfoComponent implements OnInit{

  users: User[];
  selectedUser: User;

  constructor (
    private userService: UserService
  ) {}

  getUsers(): void {
    let response = this.userService.getUsers();
    Promise.resolve(response).then(users => this.users = users).then(() => console.log(this.users));
  }

  ngOnInit(): void {
    this.getUsers();
  }



  // Change the selector if needed
  /*var $table = $('table.users');
  var $bodyCells = $table.find('tbody tr:first').children();
  colWidth: number;

  // Adjust the width of thead cells when window resizes
  $(window).resize(function() {
    // Get the tbody columns width array
    colWidth = $bodyCells.map(function() {
      return $(this).width();
    }).get();

    // Set the width of thead columns
    $table.find('thead tr').children().each(function(i, v) {
      $(v).width(colWidth[i]);
    });
  }).resize(); // Trigger resize handler*/
}
