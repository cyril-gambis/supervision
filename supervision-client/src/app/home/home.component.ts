import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/technical/authentication.service';
import { User } from '../shared/models/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  constructor(private authenticationService : AuthenticationService) { }

  ngOnInit() {
  }

  logout() {
    this.authenticationService.logout();
  }

  get currentUser(): User {
    return this.authenticationService.currentUser;
  }

}
