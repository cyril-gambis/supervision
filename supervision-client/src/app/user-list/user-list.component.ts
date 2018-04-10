import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/technical/authentication.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  theTestValue = '';

  theTestValue2 = '';

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  testValue() {
    this.http.get<string>('/api/user/1')
      .subscribe(
        data => this.theTestValue = data,
        error => this.theTestValue = 'Error'
      );
  }

  testValue2() {
    this.http.get<string>('/api/currentAccount')
      .subscribe(
        data => this.theTestValue2 = data,
        error => this.theTestValue = 'Error'
      );
  }

}
