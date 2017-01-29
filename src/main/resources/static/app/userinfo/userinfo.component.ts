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

  private users: User[] = [ new User() ];
  private selectedUser: User = new User();
  private edited = false;
  private searchtext : string;

  constructor (
    private userService: UserService
  ) {}

  onSelect(idNum: number): void{
    console.log('onSelect() ' + idNum);
    for (let u of this.users){
      if(u.brukerId ==idNum){
        this.selectedUser=u;
        this.userService.setEditUser(u);
        console.log(u);
      }
    }

  }
  searchUsers(): void {
    // this.getUsers();
    console.log("søking");
    this.userService.getUsers1().subscribe(ret => {
      this.users = this.userService.mapUsersFromObs(ret);
      if (this.searchtext != "") {
        let textlowercase = this.searchtext.toLowerCase();
        console.log(textlowercase);
        let funnet: User[] = new Array();
        for (let u of this.users) {
          if (u.fornavn.toLowerCase().includes(textlowercase) || u.etternavn.toLowerCase().includes(textlowercase)
            || u.epost.toLowerCase().includes(textlowercase) || u.stillingsBeskrivelse.toLowerCase().includes(textlowercase)
            || u.telefonNr.toString().toLowerCase().includes(textlowercase)) {
            funnet.push(u);
          }
        }
        this.users = funnet;
      }
    });
    /* else {
     this.getUsers();
     }*/
  }


  slett(user: User): void {
    this.userService.delete(user).subscribe(ret => {
      this.getUsers();
    });

  }

  //updateUsers(): void {
  //  this.userService.getUsers().then(users => this.users = users);
  //}

  getUsers(): void {
    this.userService.getUsers1().subscribe(ret => this.users = this.userService.mapUsersFromObs(ret));
  }

  getUserArray() : User[] {
    let response = this.userService.getUsers();
    let ret : User[] = new Array();
    Promise.resolve(response).then(users => ret = users);
    return ret;
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
