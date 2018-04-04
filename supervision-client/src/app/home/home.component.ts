import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  constructor(private authenticationService : AuthenticationService) { }

  ngOnInit() {
    this.authenticationService.checkCredentials();
  }

  logout() {
    this.authenticationService.logout();
  }

}
