import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/services/authentication.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  theTestValue = '';

  constructor(private authenticationService : AuthenticationService) { }

  ngOnInit() {
  }

  testValue() {
    this.authenticationService.getResource('http://localhost:8091/api/v1.0/user/1')
      .subscribe(
        data => this.theTestValue = data,
        error => this.theTestValue = 'Error'
      );
  }

  testValue2() {
    this.authenticationService.getResource('http://localhost:8091/api/v1.0/user2/1')
      .subscribe(
        data => this.theTestValue = data,
        error => this.theTestValue = 'Error'
      );
  }

}
