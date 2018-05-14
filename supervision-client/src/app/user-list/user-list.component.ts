import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/technical/authentication.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../shared/models/user';
import { UserService } from '../shared/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  user: User = undefined;

  name = '';

  users: User[] = [];

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  resetSearch() {
    this.user = undefined;
    this.users = [];
    if (this.name) {
      if (Number(this.name) > 0) {
        this.userService.getUserById(Number(this.name))
          .subscribe(res => {
              this.user = res;
          });
      } else {
        this.userService.getUserByFreeText(this.name)
          .subscribe(res => {
            if (res.length === 1) {
              this.user = res[0];
            } else {
              this.users = res;
            }
          });
      }
    }
  }

  selectUser(user: User) {
    this.user = user;
  }

}
