import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/technical/authentication.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../shared/models/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  userByMail: User = undefined;

  userById: User = undefined;

  id: number = undefined;
  mail = '';

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  findByMail() {
    /*
    this.http.get<string>('/api/currentAccount')
      .subscribe(
        data => this.theTestValue2 = data,
        error => this.theTestValue = 'Error'
      );*/
      if (this.mail) {
        this.http.get<User>('/api/users/search/findByMail' + '?mail=' + this.mail)
          .subscribe(
            data => this.userByMail = data,
            error => this.userByMail = undefined
          );
      }
  }

  findById() {
    /*
    this.http.get<string>('/api/currentAccount')
      .subscribe(
        data => this.theTestValue2 = data,
        error => this.theTestValue = 'Error'
      );*/
      if (this.id) {
        this.http.get<User>('/api/users/' + this.id)
          .subscribe(
            data => this.userById = data,
            error => this.userById = undefined
          );
      }      
  }

}
